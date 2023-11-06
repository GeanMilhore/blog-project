package com.headblog.blog.mapper;

import com.headblog.blog.domain.user.User;
import com.headblog.blog.dto.CreateUserDto;
import com.headblog.blog.dto.EditUserDto;
import com.headblog.blog.dto.ResponseUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User mapToUser(CreateUserDto createUserDto){
        return modelMapper.map(createUserDto, User.class);
    }

    public void patchUser(User user, EditUserDto createUserDto){
        modelMapper.map(createUserDto, user);
    }

    public ResponseUserDto mapToResponseUserDto(User user){
        return modelMapper.map(user, ResponseUserDto.class);
    }
}
