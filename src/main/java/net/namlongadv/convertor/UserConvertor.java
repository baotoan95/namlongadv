package net.namlongadv.convertor;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.UserDTO;
import net.namlongadv.entities.User;

public class UserConvertor {
	public static UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}
	
	public static User convertToEntity(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user;
	}
}
