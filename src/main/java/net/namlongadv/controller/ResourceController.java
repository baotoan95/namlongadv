package net.namlongadv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resources")
@Slf4j
public class ResourceController {
	@SuppressWarnings("resource")
	@RequestMapping(value = "images", method = RequestMethod.GET)
	public void responseData(@RequestParam("url") String path, HttpServletResponse response) {
		try {
			File file = new File(path);
			byte[] rs = new byte[(int)file.length()];
			new FileInputStream(file).read(rs);
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(rs);
			response.getOutputStream().close();
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
	}
}
