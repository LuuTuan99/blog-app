package com.vano.controller.admin;

import com.vano.entity.BlogAuthor;
import com.vano.service.admin.BlogAuthorServiceImpl;


import com.vano.specification.BlogAuthorSpecification;
import com.vano.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(method = RequestMethod.GET, value = "/index.html")
    public String index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            Model model) {

        // Search
        Specification specification = Specification.where(null);
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new BlogAuthorSpecification(new SearchCriteria("username", "=", keyword)))
                    .or(new BlogAuthorSpecification(new SearchCriteria("role", "=", keyword)));
        }

        Page<BlogAuthor> blogAuthorPage = blogAuthorService.findAllActive(specification, PageRequest.of(page - 1, limit));
        model.addAttribute("keyword", keyword);

        model.addAttribute("blogAuthor", blogAuthorPage.getContent());
        model.addAttribute("currentPage", blogAuthorPage.getPageable().getPageNumber() + 1);
        model.addAttribute("limit", blogAuthorPage.getPageable().getPageSize());
        model.addAttribute("totalPage", blogAuthorPage.getTotalPages());
        return "admin/author/index";
    }
}
