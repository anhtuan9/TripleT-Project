package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.MyFile;
import com.tripleT.Blog.Blog.model.User;
import com.tripleT.Blog.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("user/info")
    public String getinfo(){
        return "user/info";
    }

    @GetMapping("/user/edit-user/{name}")
    public ModelAndView showEditForm(@PathVariable String name) {
        User user = userService.findByUsername(name);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/user/edit");
            modelAndView.addObject("user", user);
            modelAndView.addObject("myFile", new MyFile());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }




}
