package net.namlongadv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/", "/view" }, method = RequestMethod.GET)
    public String view() {
        return "view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "create";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user";
    }
}
