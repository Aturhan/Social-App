package com.abdullah.socialapp.dto;

import lombok.Builder;

@Builder
public record FollowUserRequest(
        String username,
        String yourUsername
) {
}
