package net.namlongadv.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.common.PathContants;

@Slf4j
public class UploadFileUtils {
	/**
	 * 
	 * @param files
	 * @return list of path files
	 */
	public static List<String> uploadMultipleFile(List<MultipartFile> files, int reduce) {
		List<String> pathFilesUploaded = new ArrayList<>();
		File dir = new File(PathContants.UPLOAD_PATH);

		try {
			for (MultipartFile mpf : files) {
				log.info("Uploading: " + mpf.getOriginalFilename());
				String pathFile = dir.getAbsolutePath() + File.separator + new Date().getTime() + mpf.getOriginalFilename();
				if(mpf.getSize() > reduce) {
					log.debug("Reducing size of image");
					File file = FileUtils.convertMultipartToFile(mpf);
					try {
						String pathUploaded = ImageUtils.reduceImageFileSize(reduce, file, pathFile);
						log.debug("Saved to storage: {}", pathUploaded);
						pathFilesUploaded.add(pathUploaded);
					} catch (Exception e) {
						log.error("Fail to upload file with large size: " + e.getMessage());
					}
				} else {
					File containFolder = new File(dir.getAbsolutePath());
					containFolder.mkdirs();
					File serverFile = new File(pathFile);
					serverFile.createNewFile();
					log.debug("Upload to " + pathFile);
					if(mpf.getBytes().length > 0) {
						FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(serverFile));
						log.info(mpf.getOriginalFilename() + " uploaded! ");
						pathFilesUploaded.add(serverFile.getPath());
					}
				}
			}
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
		return pathFilesUploaded;
	}
}
