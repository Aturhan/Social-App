package com.abdullah.socialapp.dto;

import lombok.Builder;

@Builder
public record CreatePostResponse(
        String postId,
        String content,
        String postedBy,
        Integer likes
) {
}
