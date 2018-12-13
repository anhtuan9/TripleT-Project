package com.tripleT.Blog.Blog.repository;

import com.tripleT.Blog.Blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
