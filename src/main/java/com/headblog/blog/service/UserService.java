package com.headblog.blog.service;

import com.headblog.blog.domain.Status;
import com.headblog.blog.domain.User;
import com.headblog.blog.dto.CreateUserDto;
import com.headblog.blog.dto.ResponseUserDto;
import com.headblog.blog.mapper.UserMapper;
import com.headblog.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public ResponseUserDto createUser(CreateUserDto userDto){
        User toBeSaved = userMapper.mapToUser(userDto);
        toBeSaved.setStatus(Status.ACTIVE);
        User savedUser = userRepository.save(toBeSaved);
        return userMapper.mapToResponseUserDto(savedUser);
    }
}
