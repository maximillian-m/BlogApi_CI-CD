package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.AuthResponse;
import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Service.CommentsService;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Admin")
public class AdminController {
    private final UserService userService;
    private final CommentsService commentsService;

    public AdminController(UserService userService, CommentsService commentsService) {
        this.userService = userService;
        this.commentsService = commentsService;
    }

//This end point registers a user as an admin
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            session.invalidate();
            return new ResponseEntity<>("You are now logged out", HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not logged in yet", HttpStatus.FORBIDDEN);
    }
//An admin can be able delete a comment
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<String> deleteComment(
            HttpServletRequest req,
            @PathVariable Long id,
            CommentDto commentDto) throws CustomException {

        commentDto.setId(id);
        commentsService.deleteCommentAdmin(commentDto.getId());
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
