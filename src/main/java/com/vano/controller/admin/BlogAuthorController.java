package com.vano.controller.admin;

import com.vano.entity.BlogAuthor;
import com.vano.service.BlogAuthorServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping(value = "/blogAuthor")
public class BlogAuthorController {
    @Autowired
    BlogAuthorServiceImpl blogAuthorService;

    @RequestMapping(method = RequestMethod.GET, value = "/login.html")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register.html")
    public String create(Model model) {
        model.addAttribute("blogAuthor", new BlogAuthor());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register.html")
    public String stores(Model model, BlogAuthor blogAuthor, HttpServletRequest request) {
       try {
           String userNameInput = request.getParameter("username");
           BlogAuthor existBlogAuthor = blogAuthorService.getByName(userNameInput);
           model.addAttribute("existBlogAuthor", existBlogAuthor);

           blogAuthorService.register(blogAuthor);
       } catch (Exception ex) {
           System.out.println(ex.getStackTrace());
       }
       return "redirect:/blogAuthor/login.html";
    }
}
