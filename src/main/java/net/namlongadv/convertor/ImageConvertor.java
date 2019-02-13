package net.namlongadv.convertor;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.ImageDTO;
import net.namlongadv.entities.AdvImage;

public class ImageConvertor {
	public static ImageDTO convertToDTO(AdvImage image) {
		ImageDTO imageDTO = new ImageDTO();
		BeanUtils.copyProperties(image, imageDTO);
		return imageDTO;
	}
}
