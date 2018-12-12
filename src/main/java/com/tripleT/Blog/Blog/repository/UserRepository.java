package com.tripleT.Blog.Blog.repository;

import com.tripleT.Blog.Blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername (String username);
    Page<User> findAllByNickname(String nickname, Pageable pageable);
}
