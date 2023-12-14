package com.abdullah.socialapp.dto;

import lombok.Builder;

@Builder
public record LikePostRequest(
        String postId,
        String username
) {
}
