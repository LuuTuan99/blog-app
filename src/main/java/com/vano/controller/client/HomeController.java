package com.vano.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index() {
        return "home/index";
    }

    @RequestMapping(value = "/contact.html", method = RequestMethod.GET)
    public String contact() {
        return "home/contact";
    }

    @RequestMapping(value = "/categories.html", method = RequestMethod.GET)
    public String categories() {
        return "home/categories";
    }

    @RequestMapping(value = "/blog-single.html", method = RequestMethod.GET)
    public String blogSingle() {
        return "home/blog-single";
    }
}
