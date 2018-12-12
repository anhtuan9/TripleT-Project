package com.tripleT.Blog.Blog.service;

import com.tripleT.Blog.Blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {
    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    void save(User user);

    void remove(Long id);
    Page<User> findAllByNickname(String nickname, Pageable pageable);

}
