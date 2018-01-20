package net.namlongadv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Value("${namlongadv.session.name.page-index}")
	private String pageIndex;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/adv/view?page=0&size=10";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, ModelMap model, HttpSession session) {
		if (session.getAttribute("pageSize") == null) {
			session.setAttribute("pageSize", 10);
		}
		if(error != null) {
			log.debug("Authentication fail");
			model.addAttribute("errorMsg", "Sai tên đăng nhập hoặc mật khẩu");
		}
		return "login";
	}

}
