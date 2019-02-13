package net.namlongadv.convertor;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.RoleDTO;
import net.namlongadv.entities.UserRole;

public class RoleConvertor {
	public static RoleDTO convertToDTO(UserRole role) {
		RoleDTO roleDTO = new RoleDTO();
		BeanUtils.copyProperties(role, roleDTO);
		return roleDTO;
	}
}
