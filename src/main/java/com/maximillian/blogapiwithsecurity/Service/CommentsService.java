package com.maximillian.blogapiwithsecurity.Service;

import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Dto.CommentResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;

import java.util.List;

public interface CommentsService {


    CommentDto createComment(CommentDto commentDto, String username, Long id);

    void deleteComment(Long id, String username) throws CustomException;

    CommentDto UpdateComment(CommentDto commentDto, String username) throws CustomException;

    CommentDto viewComment(CommentDto commentDto) throws CustomException;

    List<CommentDto> searchComment(PostsDto postsDto, String typed) throws CustomException;

    CommentResponse findPage(Long postId, int pageNum, int pageSize);

    void deleteCommentAdmin(Long id) throws CustomException;
}
