package com.abdullah.socialapp.controller;

import com.abdullah.socialapp.dto.AuthRequest;
import com.abdullah.socialapp.dto.CreateUserRequest;
import com.abdullah.socialapp.service.JwtService;
import com.abdullah.socialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager manager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.manager = manager;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Void> addUser(@RequestBody CreateUserRequest request){
        userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping(path = "/login")
    public String login(@RequestBody AuthRequest request){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));
        if (authentication.isAuthenticated()) {
                return jwtService.generateToken(request.username());

            }
        throw new UsernameNotFoundException("Username is invalid");
    }
}
