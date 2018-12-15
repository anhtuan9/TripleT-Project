package com.tripleT.Blog.Blog.controller;

import com.tripleT.Blog.Blog.model.Blog;
import com.tripleT.Blog.Blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("admin/blog")
    public ModelAndView listBlog(@RequestParam(name = "blog",required = false) Optional<String> b,
                                  @RequestParam(name = "selection",required = false, defaultValue = "author") String s,
                                  Pageable pageable){
        ModelAndView list = new ModelAndView("admin/blog/list");
        Page<Blog> blogs;
        if(s.equals("tag")){
            if (b.isPresent()) {
                blogs = blogService.findAllByTagsContaining(b.get(), pageable);
            } else {
                blogs = blogService.findAll(pageable);
            }
            list.addObject("blogs", blogs);
            return list;
        }else if(s.equals("author")){
            if (b.isPresent()) {
                blogs = blogService.findAllByAuthorContaining(b.get(), pageable);
            } else {
                blogs = blogService.findAll(pageable);
            }
            list.addObject("blogs", blogs);
            return list;
        }else if(s.equals("title")){
            if (b.isPresent()) {
                blogs = blogService.findAllByTitleContaining(b.get(), pageable);
            } else {
                blogs = blogService.findAll(pageable);
            }
            list.addObject("blogs", blogs);
            return list;
        }
        return list;
    }
    @GetMapping("/create-blog")
    public ModelAndView showCreateBlog(){
        ModelAndView showCreate = new ModelAndView("admin/blog/create");
        showCreate.addObject("blog", new Blog());
        return showCreate;
    }
    @PostMapping("/create-blog")
    public ModelAndView createBlog(@Validated @ModelAttribute("blog") Blog blog, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            ModelAndView error = new ModelAndView("admin/blog/create");
            return error;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        blog.setDate(dateFormat.format(date));
        blogService.save(blog);
        ModelAndView createBlog = new ModelAndView("admin/blog/create");
        createBlog.addObject("blog", new Blog());
        createBlog.addObject("message", "Create successfully!");
        return createBlog;
    }
    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if(blog != null){
            ModelAndView editForm = new ModelAndView("admin/blog/edit");
            editForm.addObject("blog", blog);
            return editForm;
        }else{
            ModelAndView error = new ModelAndView();
            return error;
        }
    }
    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@Validated @ModelAttribute("blog") Blog blog, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            ModelAndView error = new ModelAndView("admin/blog/edit");
            return error;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        blog.setDate(dateFormat.format(date));
        blogService.save(blog);
        ModelAndView update = new ModelAndView("admin/blog/edit");
        update.addObject("blog", blog);
        update.addObject("message", "Update successfully!");
        return update;
    }
    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if(blog != null){
            ModelAndView delete = new ModelAndView("admin/blog/delete");
            delete.addObject("blog", blog);
            return delete;
        }else {
            ModelAndView error = new ModelAndView();
            return error;
        }
    }
    @PostMapping("/delete-blog")
    public String deleteBlog(@ModelAttribute("blog") Blog blog){
        blogService.remove(blog.getId());
        return "redirect:admin/blog";
    }
    @GetMapping("/view-blog/{id}")
    public ModelAndView viewBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if(blog != null){
            ModelAndView view = new ModelAndView("admin/blog/view");
            view.addObject("blog", blog);
            return view;
        }else{
            ModelAndView error = new ModelAndView();
            return error;
        }
    }
}
