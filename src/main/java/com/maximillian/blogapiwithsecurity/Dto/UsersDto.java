package com.maximillian.blogapiwithsecurity.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    Long id;
    @NotBlank(message = "UserName cannot be blank")
    private String userName;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;

}
