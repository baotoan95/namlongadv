package net.namlongadv.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resources")
@Slf4j
public class ResourceController {
	@RequestMapping(value = "images", method = RequestMethod.GET)
	public void responseData(@RequestParam("url") String path, HttpServletResponse response) {
		log.debug(path);
		try {
			File file = new File(path);

			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			// Caching
			response.setHeader("cache-control", "max-age=31536000");
			response.setHeader("Expires", "31536000");
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(response.getOutputStream());
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			IOUtils.copy(bufferedInputStream, bufferedOutput);
			
			bufferedOutput.flush();
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
	}
}
