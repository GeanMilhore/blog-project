package com.headblog.blog.controller;

import com.headblog.blog.domain.User;
import com.headblog.blog.dto.CreateUserDto;
import com.headblog.blog.dto.EditUserDto;
import com.headblog.blog.dto.ResponseUserDto;
import com.headblog.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<ResponseUserDto>> findAllUsers(Pageable pageable){
        return new ResponseEntity<>(userService.findAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody @Valid CreateUserDto userDto){
        ResponseUserDto responseDto = userService.createUser(userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDto> editUser(@PathVariable Long id,@RequestBody @Valid EditUserDto editUserDto){
        ResponseUserDto responseDto = userService.editUser(id, editUserDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUserDto> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
