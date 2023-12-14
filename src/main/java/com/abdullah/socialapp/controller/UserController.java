package com.abdullah.socialapp.controller;

import com.abdullah.socialapp.dto.CreateUserRequest;
import com.abdullah.socialapp.dto.FollowUserRequest;
import com.abdullah.socialapp.dto.SearchUserResponse;
import com.abdullah.socialapp.exception.UserAlreadyFollowedException;
import com.abdullah.socialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(path = "/get")
    public ResponseEntity<SearchUserResponse> getUser(@RequestParam ("username") String username){
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByUsername(username));
    }
    @GetMapping(path = "/getAll")
    public ResponseEntity<List<SearchUserResponse>> getAllUsers(){
        List<SearchUserResponse> list = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PatchMapping(path = "/follow")
    public ResponseEntity<Void> follow(@RequestBody FollowUserRequest request) throws UserAlreadyFollowedException {
        userService.followUser(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping(path = "/delete")
    public String delete(@RequestParam ("username") String username){
        userService.removeUser(username);
        return "User deleted successfully!";
    }
}
