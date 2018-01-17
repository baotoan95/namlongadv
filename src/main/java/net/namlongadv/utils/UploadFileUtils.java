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
		log.debug("Uploading {} images", files.size());

		File file = null;
		String pathUploaded = null;
		File serverFile = null;
		File containFolder = null;
		String pathFile = null;

		try {
			for (MultipartFile mpf : files) {
				if (mpf.getSize() > 0) {
					log.info("Uploading: " + mpf.getOriginalFilename());
					pathFile = dir.getAbsolutePath() + File.separator + new Date().getTime()
							+ mpf.getOriginalFilename();
					containFolder = new File(dir.getAbsolutePath());
					containFolder.mkdirs();

					// Resize
					if (mpf.getSize() > reduce) {
						log.debug("Reducing size of image");
						file = FileUtils.convertMultipartToFile(mpf);
						try {
							pathUploaded = ImageUtils.reduceImageFileSize(reduce, file, pathFile);
							log.debug("Saved to storage: {}", pathUploaded);
							pathFilesUploaded.add(pathUploaded);
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					// Normal case
					} else {
						serverFile = new File(pathFile);
						serverFile.createNewFile();
						log.debug("Upload to " + pathFile);
						if (mpf.getBytes().length > 0) {
							FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(serverFile));
							log.info(mpf.getOriginalFilename() + " uploaded! ");
							pathFilesUploaded.add(serverFile.getPath());
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
		return pathFilesUploaded;
	}
}
