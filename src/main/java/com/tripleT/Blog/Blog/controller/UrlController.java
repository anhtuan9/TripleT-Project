package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.Blog;
import com.tripleT.Blog.Blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UrlController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/")
    public ModelAndView index(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Blog> blogs;
        blogs = blogService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;

    }


    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @GetMapping("/mem-view-blog/{id}")
    public ModelAndView USER_viewBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView view = new ModelAndView("/view");
            view.addObject("blog", blog);
            return view;
        } else {
            return new ModelAndView();
        }
    }

}
