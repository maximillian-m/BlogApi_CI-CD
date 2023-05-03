package com.maximillian.blogapiwithsecurity.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
public class AuthResponse implements Serializable {
    @JsonProperty("access_token")
    private String Token;
}
