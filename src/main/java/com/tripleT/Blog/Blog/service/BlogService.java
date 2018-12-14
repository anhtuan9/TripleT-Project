package com.tripleT.Blog.Blog.service;

import com.tripleT.Blog.Blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);
    Blog findById(Long id);
    void save(Blog blog);
    void remove(Long id);
    Page<Blog> findAllByAuthorContaining(String author, Pageable pageable);
    Page<Blog> findAllByTagsContaining(String tags, Pageable pageable);
    Page<Blog> findAllByTitleContaining(String titel, Pageable pageable);
}
