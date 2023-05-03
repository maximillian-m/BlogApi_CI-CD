package com.maximillian.blogapiwithsecurity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private List<CommentDto> ContentList;
    private int PageNo;
    private int PageSize;
    private int totalPages;
    private int totalElements;
    private boolean Last;
    private boolean First;
}
