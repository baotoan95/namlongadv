package net.namlongadv.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.namlongadv.constant.ErrorMessageCommon;
import net.namlongadv.convertor.UserConvertor;
import net.namlongadv.dto.PageDTO;
import net.namlongadv.dto.UserDTO;
import net.namlongadv.entities.User;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    public User getUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    
    public UserDTO getUserById(UUID userId) throws BadRequestException {
    	User user = userRepository.findOne(userId);
    	if(user != null) {
    		return UserConvertor.convertToDTO(user);
    	}
    	throw new BadRequestException(ErrorMessageCommon.USER_NOT_EXISTED);
    }
    
    public PageDTO<UserDTO> getAll(int page, int size) {
    	Page<User> userPage = userRepository.findAll(new PageRequest(page, size, Direction.ASC, "updatedDate"));
    	if(userPage != null) {
	    	List<UserDTO> users = userPage.getContent().stream().map(user -> {
	    		return UserConvertor.convertToDTO(user);
	    	}).collect(Collectors.toList());
	    	return new PageDTO<UserDTO>(userPage.getTotalElements(), users);
    	} else {
    		return new PageDTO<>();
    	}
    }
    
    public UserDTO create(UserDTO userDTO) throws BadRequestException {
    	verify(userDTO);
    	
    	User existedUser = userRepository.findByUsername(userDTO.getUsername());
    	if(existedUser != null) {
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
    	if(existedUser == null) {
    		throw new BadRequestException(ErrorMessageCommon.USER_NOT_EXISTED);
    	} else {
    		User newUser = UserConvertor.convertToEntity(userDTO);
    		if(!StringUtils.isEmpty(userDTO.getNewPassword()) 
    				&& !passwordEncoder.matches(userDTO.getNewPassword(), existedUser.getPassword())) {
    			newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    		}
    		return UserConvertor.convertToDTO(userRepository.save(newUser));
    	}
    }
    
    private void verify(UserDTO userDTO) throws BadRequestException {
    	if(StringUtils.isEmpty(userDTO.getUsername()) || Objects.isNull(userDTO.getId()) || StringUtils.isEmpty(userDTO.getEmail())
    			|| StringUtils.isEmpty(userDTO.getName()) || StringUtils.isEmpty(userDTO.getPassword())) {
    		throw new BadRequestException(ErrorMessageCommon.USER_REQUIRED_FIELDS);
    	}
    }
}
