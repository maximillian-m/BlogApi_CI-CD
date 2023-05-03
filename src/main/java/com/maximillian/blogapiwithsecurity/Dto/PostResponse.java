package com.maximillian.blogapiwithsecurity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostsDto> responsePost;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPage;
    private boolean isFirst;
    private boolean isLast;

}
