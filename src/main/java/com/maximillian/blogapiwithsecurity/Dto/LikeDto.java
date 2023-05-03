package com.maximillian.blogapiwithsecurity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    Long likeId;
    Long User_id;
    Long Post_id;
}
