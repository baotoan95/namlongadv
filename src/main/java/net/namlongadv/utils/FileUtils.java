package net.namlongadv.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	private FileUtils() {}
	
	public static String getExtensions(String path) {
		return path.substring(path.lastIndexOf('.') + 1, path.length());
	}

	public static File convertMultipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		try {
			multipart.transferTo(convFile);
		} catch (Exception e) {
			return convFile;
		}
		return convFile;
	}
}
