package net.namlongadv.services;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.constant.ErrorMessageCommon;
import net.namlongadv.convertor.UserConvertor;
import net.namlongadv.dto.PageDTO;
import net.namlongadv.dto.UserDTO;
import net.namlongadv.entities.User;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.repositories.UserRepository;
import net.namlongadv.specs.UserSpecificationBuilder;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ObjectMapper objectMapper;

	public User getUser(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	public UserDTO getUserById(UUID userId) throws BadRequestException {
		User user = userRepository.findOne(userId);
		if (user != null) {
			return UserConvertor.convertToDTO(user);
		}
		throw new BadRequestException(ErrorMessageCommon.USER_NOT_EXISTED);
	}

	public PageDTO<UserDTO> getAll(String filter, int page, int size) {
		page = page - 1;
		size = size <= -1 ? Integer.MAX_VALUE : size;
		filter = net.namlongadv.utils.StringUtils.isEmptyOrNull(filter) ? "[]" : filter;
		List<SearchCriteria> params;
		try {
			params = objectMapper.readValue(filter, new TypeReference<List<SearchCriteria>>() { });
			Specification<User> specification = new UserSpecificationBuilder(params).build();
			Page<User> userPage = userRepository.findAll(specification, new PageRequest(page, size, Direction.ASC, "updatedDate"));
			if (userPage != null) {
				List<UserDTO> users = userPage.getContent().stream().map(user -> {
					return UserConvertor.convertToDTO(user);
				}).collect(Collectors.toList());
				return new PageDTO<UserDTO>(userPage.getTotalElements(), users);
			}
		} catch (IOException e) {
			// Do nothing
		}
		return new PageDTO<>();
	}

	public UserDTO create(UserDTO userDTO) throws BadRequestException {
		verify(userDTO);

		User existedUser = userRepository.findByUsername(userDTO.getUsername());
		if (existedUser != null) {
			throw new BadRequestException(ErrorMessageCommon.USER_EXISTED);
		} else {
			User newUser = UserConvertor.convertToEntity(userDTO);
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			return UserConvertor.convertToDTO(userRepository.save(newUser));
		}
	}

	public boolean delete(UUID userId) {
		try {
			userRepository.delete(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public UserDTO save(UserDTO userDTO) throws BadRequestException {
		verify(userDTO);

		User existedUser = userRepository.findOne(userDTO.getId());
		if (existedUser == null) {
			throw new BadRequestException(ErrorMessageCommon.USER_NOT_EXISTED);
		} else {
			User newUser = UserConvertor.convertToEntity(userDTO);
			if (!StringUtils.isEmpty(userDTO.getNewPassword())
					&& !passwordEncoder.matches(userDTO.getNewPassword(), existedUser.getPassword())) {
				newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			}
			return UserConvertor.convertToDTO(userRepository.save(newUser));
		}
	}

	private void verify(UserDTO userDTO) throws BadRequestException {
		if (StringUtils.isEmpty(userDTO.getUsername()) || Objects.isNull(userDTO.getId())
				|| StringUtils.isEmpty(userDTO.getEmail()) || StringUtils.isEmpty(userDTO.getName())
				|| StringUtils.isEmpty(userDTO.getPassword())) {
			throw new BadRequestException(ErrorMessageCommon.USER_REQUIRED_FIELDS);
		}
	}
}
