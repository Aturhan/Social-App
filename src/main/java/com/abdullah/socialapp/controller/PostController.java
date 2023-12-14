package com.abdullah.socialapp.controller;

import com.abdullah.socialapp.dto.CreatePostRequest;
import com.abdullah.socialapp.dto.CreatePostResponse;
import com.abdullah.socialapp.dto.LikePostRequest;
import com.abdullah.socialapp.dto.SearchPostResponse;
import com.abdullah.socialapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping(path = "/get")
    public ResponseEntity<SearchPostResponse> getPost(@RequestParam ("id") String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(postService.getPostById(id));
    }
    @PostMapping(path = "/add")
    public ResponseEntity<CreatePostResponse> addPost(@RequestBody CreatePostRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(request));
    }
    @PatchMapping(path = "/like")
    public void likePost(@RequestBody LikePostRequest request){
        postService.likePost(request);
    }
    @DeleteMapping(path = "/delete")
    public  void deletePost(@RequestParam ("id") String id){
        postService.deletePost(id);
    }
}
