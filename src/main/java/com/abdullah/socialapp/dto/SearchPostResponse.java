package com.abdullah.socialapp.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchPostResponse(
        String username,
        String postId,
        String content,
        String likes,
        LocalDateTime createdAt
) {
}
