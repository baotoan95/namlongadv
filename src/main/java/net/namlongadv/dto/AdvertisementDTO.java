package net.namlongadv.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.namlongadv.entities.Advertisement;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Advertisement advertisement = new Advertisement();
	private List<MultipartFile> files = new ArrayList<>();
	private UUID[] prevImages = new UUID[6]; 
	private MultipartFile map;
	private boolean ignoreError = false;
}
