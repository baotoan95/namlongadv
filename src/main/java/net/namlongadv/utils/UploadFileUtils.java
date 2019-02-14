package net.namlongadv.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.constant.PathContants;
import net.namlongadv.dto.ImageDTO;

@Slf4j
public class UploadFileUtils {
	/**
	 * 
	 * @param files
	 * @return list of path files
	 */
	public List<String> uploadMultipleFile(List<Object> files, int reduce, boolean isMap) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> pathFilesUploaded = new ArrayList<>();
		File dir = new File(PathContants.UPLOAD_PATH);
		log.debug("Uploading {} images", files.size());

		File file = null;
		String pathUploaded = null;
		File serverFile = null;
		File containFolder = null;
		String pathFile = null;

		try {
			for (Object obj : files) {
				if (obj instanceof MultipartFile && ((MultipartFile)obj).getSize() > 0) {
					final MultipartFile mpf = (MultipartFile) obj;
					String originalFileName = mpf.getOriginalFilename();
					String fileName = StringUtils.standardize(StringUtils.convertStringIgnoreUtf8(originalFileName.substring(0, originalFileName.lastIndexOf("."))));
					fileName = fileName.replaceAll(" ", "-").replaceAll("\\+", "");
					log.info("Uploading: " + originalFileName);
					if (isMap) {
						pathFile = dir.getAbsolutePath() + File.separator + fileName + "-" + new Date().getTime() + "map."
								+ FileUtils.getExtensions(mpf.getOriginalFilename());
					} else {
						pathFile = dir.getAbsolutePath() + File.separator + fileName + "-" + new Date().getTime() + "NL."
								+ FileUtils.getExtensions(mpf.getOriginalFilename());
					}
					log.info("Upload to " + pathFile);
					containFolder = new File(dir.getAbsolutePath());
					containFolder.mkdirs();

					// Resize
					if (mpf.getSize() > reduce && !FileUtils.getExtensions(mpf.getOriginalFilename()).equalsIgnoreCase("png")) {
						log.debug("Reducing size of image");
						file = FileUtils.convertMultipartToFile(mpf);
						try {
							pathUploaded = ImageUtils.reduceImageFileSize(file, pathFile);
							log.debug("Saved to storage: {}", pathUploaded);
							pathFilesUploaded.add(pathUploaded);
						} catch (Exception e) {
							// Do not reduce if file type invalid
							serverFile = new File(pathFile);
							serverFile.createNewFile();
							log.debug("Upload to " + pathFile);
							org.apache.commons.io.FileUtils.copyFile(file, serverFile);
							log.info(mpf.getOriginalFilename() + " uploaded! ");
							pathFilesUploaded.add(serverFile.getPath());
						}
					} else {
						// Normal case
						serverFile = new File(pathFile);
						log.debug("Upload to " + pathFile);
						if (serverFile.createNewFile() && mpf.getBytes().length > 0) {
							FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(serverFile));
							log.info(mpf.getOriginalFilename() + " uploaded! ");
							pathFilesUploaded.add(serverFile.getPath());
						} else {
							log.error("Can't not create new file");
						}
					}
				} else {
					final ImageDTO image = objectMapper.readValue(obj.toString(), ImageDTO.class);
					pathFilesUploaded.add(image.getUrl());
				}
			}
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
		return pathFilesUploaded;
	}
}
