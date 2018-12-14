package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.Role;
import com.tripleT.Blog.Blog.model.User;
import com.tripleT.Blog.Blog.repository.RoleRepository;
import com.tripleT.Blog.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleRepository.findAll();
    }


    @GetMapping("/create-user")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setNickname(user.getNickname());
        newuser.setUsername(user.getUsername());
        newuser.setRole(user.getRole());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(user.getRole()));
        newuser.setRoles(roles);
        userService.save(newuser);
        ModelAndView modelAndView = new ModelAndView("/admin/create");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listUser(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<User> users;
        if (s.isPresent()) {
            users = userService.findAllByNickname(s.get(), pageable);
        } else {
            users = userService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/admin/list");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/edit");
            modelAndView.addObject("user", user);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-user")
    public ModelAndView updateUser(@ModelAttribute("user") User user) {
        User newuser = new User();
        newuser.setId(user.getId());
        newuser.setEmail(user.getEmail());
        newuser.setNickname(user.getNickname());
        newuser.setUsername(user.getUsername());
        newuser.setRole(user.getRole());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(user.getRole()));
        newuser.setRoles(roles);
        userService.save(newuser);
        ModelAndView modelAndView = new ModelAndView("/admin/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Note updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-user/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/delete");
            modelAndView.addObject("user", user);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("admin/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-user")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.remove(user.getId());
        return "redirect:list";
    }
}
