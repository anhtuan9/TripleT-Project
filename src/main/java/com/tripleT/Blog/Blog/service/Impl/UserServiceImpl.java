package com.tripleT.Blog.Blog.service.Impl;

import com.tripleT.Blog.Blog.model.User;
import com.tripleT.Blog.Blog.repository.UserRepository;
import com.tripleT.Blog.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void save(User blog) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Page<User> findAllByNickname(String nickname, Pageable pageable) {
        return userRepository.findAllByNickname(nickname, pageable);
    }

}