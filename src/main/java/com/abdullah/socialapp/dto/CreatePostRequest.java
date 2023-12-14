package com.abdullah.socialapp.dto;

import lombok.Builder;

@Builder
public record CreatePostRequest(
        String username,
        String content
) {
}
