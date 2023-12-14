package com.abdullah.socialapp.service;

import com.abdullah.socialapp.dto.*;
import com.abdullah.socialapp.exception.PostNotFoundException;
import com.abdullah.socialapp.exception.UserNotFoundException;
import com.abdullah.socialapp.model.Post;
import com.abdullah.socialapp.model.UserModel;
import com.abdullah.socialapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public SearchPostResponse getPostById(String id) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));
        return this.converter(post);
    }

    public CreatePostResponse addPost(CreatePostRequest request) {
        UserModel user = userService.findUserForPost(request.username());
        if (user == null){
            throw new UserNotFoundException("User not found!");
        }
        final Post post = Post.builder()
                .userModel(user)
                .content(request.content())
                .likes(Set.of("Social"))
                .build();

        final Post savedPost = postRepository.save(post);

        return CreatePostResponse.builder()
                .postId(savedPost.getId())
                .content(savedPost.getContent())
                .postedBy(savedPost.getUserModel().getUsername())
                .likes((savedPost.getLikes() != null) ? savedPost.getLikes().size() - 1 : 0)
                .build();
    }

    public void likePost(LikePostRequest request){
        final UserModel user = userService.findUserForPost(request.username());
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        final Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));

        post.addLike(request.username());
        postRepository.save(post);
    }

    public void deletePost(String id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));
        postRepository.delete(post);
    }

    private SearchPostResponse converter(Post post){
        return SearchPostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .username(post.getUserModel().getUsername())
                .likes((post.getLikes() != null) ? post.getLikes().toString() : "Not liked yet")
                .createdAt(post.getCreatedAt())
                .build();
    }
}
