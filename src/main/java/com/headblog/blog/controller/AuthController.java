package com.headblog.blog.controller;


import com.headblog.blog.domain.user.User;
import com.headblog.blog.dto.AuthenticationDto;
import com.headblog.blog.dto.LoginResponseDto;
import com.headblog.blog.service.ConfirmationTokenService;
import com.headblog.blog.service.TokenService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity generateToken(@RequestBody @Valid AuthenticationDto authDto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDto.login(), authDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @GetMapping("/validateUserRegistry")
    public String validateUserRegistry(@PathParam("token") String token){
        return confirmationTokenService.validateUserRegistry(token);
    }

}
