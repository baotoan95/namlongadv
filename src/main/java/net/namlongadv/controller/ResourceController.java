package net.namlongadv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.utils.FileUtils;
import net.namlongadv.utils.ImageUtils;

@RestController
@RequestMapping("resources")
@Slf4j
public class ResourceController {
	@SuppressWarnings("resource")
	@RequestMapping(value = "images", method = RequestMethod.GET)
	public void responseData(@RequestParam("url") String path,
			@RequestParam(value = "w", required = false) Optional<Integer> width,
			@RequestParam(value = "h", required = false) Optional<Integer> height, HttpServletResponse response) {
		try {
			File file = new File(path);
			
			if(width.isPresent() && width.get() > 0 || height.isPresent() && height.get() > 0) {
				file = ImageUtils.resizeImage(width.get(), height.get(), file);
			}
			
			byte[] rs = new byte[(int)file.length()];
			new FileInputStream(file).read(rs);
			response.setContentType("image/" + FileUtils.getExtensions(path));
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			response.getOutputStream().write(rs);
			response.getOutputStream().close();
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
	}
}
