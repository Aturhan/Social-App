package com.abdullah.socialapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;


    private String password;

    private boolean accountNonExpired;
    private boolean isEnabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @OneToMany(mappedBy = "userModel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private Set<String> followers = new HashSet<>();
    private Set<String> followings = new HashSet<>();

    public void addFollower(String username){
        this.followers.add(username);
    }

    public void addFollowing(String username){
        this.followings.add(username);
    }

    public void addPost(Post post){
        this.posts.add(post);
    }


    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;


}
