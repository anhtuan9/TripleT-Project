package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.MyFile;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;

@Controller
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


    @GetMapping("/admin/create-user")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/create");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("myFile", new MyFile());
        return modelAndView;
    }

    @PostMapping("/admin/create-user")
    public ModelAndView saveUser(@ModelAttribute("user") User user,MyFile myFile) {
        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setNickname(user.getNickname());
        newuser.setUsername(user.getUsername());
        newuser.setRole(user.getRole());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(user.getRole()));
        newuser.setRoles(roles);
        try {
            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(this.getFolderUpload(), fileName);
            multipartFile.transferTo(file);
            newuser.setImg("/img/"+ fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.save(newuser);
        ModelAndView modelAndView = new ModelAndView("/admin/create");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/admin/list")
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

    @GetMapping("/admin/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/edit");
            modelAndView.addObject("user", user);
            modelAndView.addObject("myFile", new MyFile());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/admin/edit-user")
    public ModelAndView updateUser(@ModelAttribute("user") User user, MyFile myFile) {
        User newuser = new User();
        newuser.setId(user.getId());
        newuser.setEmail(user.getEmail());
        newuser.setNickname(user.getNickname());
        newuser.setUsername(user.getUsername());
        newuser.setRole(user.getRole());
        newuser.setPassword(user.getPassword());
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(user.getRole()));
        newuser.setRoles(roles);
        try {
            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(this.getFolderUpload(), fileName);
            multipartFile.transferTo(file);
            newuser.setImg("/img/"+ fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.save(newuser);
        ModelAndView modelAndView = new ModelAndView("/admin/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Note updated successfully");
        return modelAndView;
    }

    @GetMapping("/admin/delete-user/{id}")
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

    @PostMapping("/admin/delete-user")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.remove(user.getId());
        return "redirect:list";
    }
    public File getFolderUpload() {
        File folderUpload = new File("D:\\project\\Blog\\src\\main\\resources\\static\\img");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
}
