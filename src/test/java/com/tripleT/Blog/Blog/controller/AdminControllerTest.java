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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Autowired
    private MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void showCreateForm() throws Exception {
        mockMvc.perform(get("/admin/create-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/create"));
    }

    @Test
    public void saveUser() throws Exception {
        mockMvc.perform(post("/admin/create-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/create"));
    }

    @Test
    public void listUser() throws Exception {
        mockMvc.perform(get("/admin/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/list"));
    }

    @Test
    public void showEditForm() throws Exception {
        mockMvc.perform(get("/admin/edit-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/edit"));

    }

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(post("/admin/edit-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/edit"));
    }

    @Test
    public void showDeleteForm() throws Exception {
        mockMvc.perform(get("/admin/delete-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/delete"));

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(post("/admin/delete-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/admin/list"));
    }
}