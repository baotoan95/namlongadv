package net.namlongadv.convertor;

import java.util.Arrays;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.UserDTO;
import net.namlongadv.entities.User;
import net.namlongadv.entities.UserRole;

public class UserConvertor {
	public static UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		userDTO.setRole(user.getRoles().size() > 0 ? user.getRoles().get(0).getId(): null);
		return userDTO;
	}
	
	public static User convertToEntity(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setRoles(Arrays.asList(UserRole.builder().id(userDTO.getRole()).build()));
		return user;
	}
}
