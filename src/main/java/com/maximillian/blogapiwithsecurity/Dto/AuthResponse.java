package com.maximillian.blogapiwithsecurity.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
public class AuthResponse {
    private String Token;
}
