package com.maximillian.blogapiwithsecurity.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostsDto {
    Long id;
    @NotBlank(message = "title must not be blank")
    private String title;
    @NotBlank(message = "content must not be blank")
    private String content;
    private String image_url;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
