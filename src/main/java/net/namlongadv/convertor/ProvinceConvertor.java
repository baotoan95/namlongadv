package net.namlongadv.convertor;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.ProvinceDTO;
import net.namlongadv.entities.Province;

public class ProvinceConvertor {
	public static ProvinceDTO convertToDTO(Province province) {
		ProvinceDTO provinceDTO = new ProvinceDTO();
		BeanUtils.copyProperties(province, provinceDTO);
		return provinceDTO; 
	}
}
