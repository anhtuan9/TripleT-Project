package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.Blog;
import com.tripleT.Blog.Blog.model.MyFile;
import com.tripleT.Blog.Blog.model.Role;
import com.tripleT.Blog.Blog.model.User;
import com.tripleT.Blog.Blog.repository.RoleRepository;
import com.tripleT.Blog.Blog.service.BlogService;
import com.tripleT.Blog.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @GetMapping("user/info")
    public ModelAndView USER_getinfo(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/info");
        User user = userService.findByUsername(principal.getName());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/user/edit-user/")
    public ModelAndView USER_showEditForm(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/user/edit-user");
            modelAndView.addObject("user", user);
            modelAndView.addObject("myFile", new MyFile());
            return modelAndView;

        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/user/edit-user")
    public ModelAndView USER_updateUser(@ModelAttribute("user") User user, MyFile myFile) {
        User newuser = new User();
        newuser.setId(user.getId());
        newuser.setEmail(user.getEmail());
        newuser.setNickname(user.getNickname());
        newuser.setUsername(user.getUsername());
        newuser.setRole("ROLE_MEMBER");
        newuser.setPassword(user.getPassword());
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_MEMBER"));
        newuser.setRoles(roles);
        try {
            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            assert fileName != null;
            File file = new File(this.USER_getFolderUpload(), fileName);
            multipartFile.transferTo(file);
            newuser.setImg("/img/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.save(newuser);
        ModelAndView modelAndView = new ModelAndView("/user/blog/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Note updated successfully");
        return modelAndView;
    }

    public File USER_getFolderUpload() {
        File folderUpload = new File("D:\\project\\Blog\\src\\main\\resources\\static\\img");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }


    @GetMapping("user/blog")
    public ModelAndView USER_listBlog(Pageable pageable, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/blog/list");
        Page<Blog> blogs;
        blogs = blogService.findAllByAuthorContaining(principal.getName(), pageable);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;


    }

    @GetMapping("user/blog/create-blog")
    public ModelAndView USER_showCreateBlog() {
        ModelAndView showCreate = new ModelAndView("user/blog/create");
        showCreate.addObject("blog", new Blog());
        return showCreate;
    }

    @PostMapping("/user-create-blog")
    public ModelAndView USER_createBlog(@ModelAttribute("blog") Blog blog, Principal principal) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        blog.setDate(dateFormat.format(date));
        blog.setAuthor_name(principal.getName());
        blogService.save(blog);
        ModelAndView createBlog = new ModelAndView("user/blog/create");
        createBlog.addObject("blog", new Blog());
        createBlog.addObject("message", "Create successfully!");
        return createBlog;
    }

    @GetMapping("user/blog/user-edit-blog/{id}")
    public ModelAndView USER_showEditBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView editForm = new ModelAndView("/user/blog/edit");
            editForm.addObject("blog", blog);
            return editForm;
        } else {
            ModelAndView error = new ModelAndView();
            return error;
        }
    }

    @PostMapping("/user-edit-blog")
    public ModelAndView USER_updateBlog(@Validated @ModelAttribute("blog") Blog blog, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView error = new ModelAndView("/user/blog/edit");
            return error;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        blog.setDate(dateFormat.format(date));
        blog.setAuthor_name(principal.getName());
        blogService.save(blog);
        ModelAndView update = new ModelAndView("/user/blog/edit");
        update.addObject("blog", blog);
        update.addObject("message", "Update successfully!");
        return update;
    }

    @GetMapping("user/blog/user-delete-blog/{id}")
    public ModelAndView USER_showDeleteBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView delete = new ModelAndView("user/blog/delete");
            delete.addObject("blog", blog);
            return delete;
        } else {
            return new ModelAndView();
        }
    }

    @PostMapping("/user-delete-blog")
    public String USER_deleteBlog(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return "redirect:user/blog";
    }

    @GetMapping("user/blog/user-view-blog/{id}")
    public ModelAndView USER_viewBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView view = new ModelAndView("/user/blog/view");
            view.addObject("blog", blog);
            return view;
        } else {
            return new ModelAndView();
        }
    }

}
