package com.maximillian.blogapiwithsecurity.Mapper;

import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Models.Comments;

public class CommentsMapper {
    public static CommentDto commentsToDto(Comments comments){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comments.getId());
        commentDto.setComments(comments.getComments());
        return commentDto;
    }
    public static Comments Dtotocomments(CommentDto commentDto){
        Comments comments = new Comments();
        comments.setId(commentDto.getId());
        comments.setComments(commentDto.getComments());
        return comments;
    }
}
