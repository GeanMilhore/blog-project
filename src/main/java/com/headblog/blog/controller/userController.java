package com.headblog.blog.controller;

import com.headblog.blog.dto.CreateUserDto;
import com.headblog.blog.dto.ResponseUserDto;
import com.headblog.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody @Valid CreateUserDto userDto){
        ResponseUserDto responseDto = userService.createUser(userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
