package com.headblog.blog.service;

import com.headblog.blog.domain.user.User;
import com.headblog.blog.domain.user.UserStatus;
import com.headblog.blog.dto.CreateUserDto;
import com.headblog.blog.dto.EditUserDto;
import com.headblog.blog.dto.ResponseUserDto;
import com.headblog.blog.mapper.UserMapper;
import com.headblog.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder bcrypt;

    public ResponseUserDto createUser(CreateUserDto userDto){

        if(userRepository.findByEmail(userDto.getEmail()) != null)
            throw new BadCredentialsException("user already exists.");

        User toBeSaved = userMapper.mapToUser(userDto);
        toBeSaved.setStatus(UserStatus.INACTIVE);
        toBeSaved.setPassword(bcrypt.encode(userDto.getPassword()));
        User savedUser = userRepository.save(toBeSaved);

        confirmationTokenService.sendUserRegistryToken(savedUser);
        return userMapper.mapToResponseUserDto(savedUser);
    }

    public ResponseUserDto editUser(Long userId, EditUserDto editUserDto){
        User foundUser = findUserById(userId);
        userMapper.patchUser(foundUser, editUserDto);
        return userMapper.mapToResponseUserDto(userRepository.save(foundUser));
    }

    public void deleteUser(Long id) {
        User userFound = findUserById(id);
        userFound.setStatus(UserStatus.INACTIVE);
        userRepository.save(userFound);
    }

    public User findUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usúario inativo ou inexistente"));
    }

    public Page<ResponseUserDto> findAllUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(user -> userMapper.mapToResponseUserDto(user));
    }
}
