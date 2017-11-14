package net.namlongadv.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.namlongadv.models.Advertisement;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Advertisement advertisement;
	private List<MultipartFile> files;
}
