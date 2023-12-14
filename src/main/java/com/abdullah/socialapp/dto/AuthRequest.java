package com.abdullah.socialapp.dto;

import lombok.Builder;

@Builder
public record AuthRequest(
        String username,
        String password
) {
}
