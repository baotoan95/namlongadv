package net.namlongadv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Value("${namlongadv.session.name.page-index}")
	private String pageIndex;
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/", "/advs" }, method = RequestMethod.GET)
	public String advs(HttpSession session) {
		log.info("Getting advs page");
		log.info(pageIndex);
		session.setAttribute(pageIndex, "advs");
		return "advs";
	}

	@RequestMapping(value = "/adv", method = RequestMethod.GET)
	public String adv(HttpSession session) {
		session.setAttribute(pageIndex, "adv");
		return "adv";
	}

}
