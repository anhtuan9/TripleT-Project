package com.tripleT.Blog.Blog.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {

    @InjectMocks
    private BlogController blogController;

    @Autowired
    private MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void listBlogs() throws Exception {
        mockMvc.perform(get("/blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/list"));
    }

    @Test
    public void showCreateBlog() throws Exception {
        mockMvc.perform(get("/create-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/create"));
    }

    @Test
    public void createBlog() throws Exception {
        mockMvc.perform(get("/create-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/create"));
    }

    @Test
    public void showEditBlog() throws Exception {
        mockMvc.perform(get("/edit-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/edit"));
    }

    @Test
    public void updateBlog() throws Exception {
        mockMvc.perform(get("/edit-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/edit"));
    }

    @Test
    public void showDeleteBlog() throws Exception {
        mockMvc.perform(get("/delete-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/delete"));
    }

    @Test
    public void deleteBlog() throws Exception {
        mockMvc.perform(get("/delete-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/delete"));
    }

    @Test
    public void viewBlog() throws Exception {
        mockMvc.perform(get("/view-blog"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/blog/view"));
    }
}