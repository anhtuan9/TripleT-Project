package com.tripleT.Blog.Blog.service;

import com.tripleT.Blog.Blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);
    Blog findById(Long id);
    void save(Blog blog);
    void remove(Long id);
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
