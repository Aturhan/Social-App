package com.abdullah.socialapp.dto;

import com.abdullah.socialapp.model.Post;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchUserResponse (
        String fullName,
        String username,
        Integer followersCount,
        Integer followingsCount,
        Integer posts
) {
}
