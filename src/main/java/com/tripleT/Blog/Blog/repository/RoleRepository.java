package com.tripleT.Blog.Blog.repository;

import com.tripleT.Blog.Blog.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends PagingAndSortingRepository<Role, Long> {
    Role findByName (String name);
}