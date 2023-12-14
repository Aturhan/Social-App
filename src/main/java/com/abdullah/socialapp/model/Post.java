package com.abdullah.socialapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String content;
    @ManyToOne
    @JoinColumn(name = "userModel")
    private UserModel userModel;

    private Set<String> likes = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void addLike(String username){
        if (likes == null){
            likes = new HashSet<>();
        }
        likes.add(username);
    }
}
