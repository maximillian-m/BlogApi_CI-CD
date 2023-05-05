package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.AuthResponse;
import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Service.CommentsService;
import com.maximillian.blogapiwithsecurity.Service.PostService;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Admin")
public class AdminController {
    private final UserService userService;
    private final CommentsService commentsService;
    private final PostService postService;



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

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<String> deleteComment(
            HttpServletRequest req,
            @PathVariable Long id,
            CommentDto commentDto) throws CustomException {

        commentDto.setId(id);
        commentsService.deleteCommentAdmin(commentDto.getId());
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/edit-post/{id}")
    public ResponseEntity<String> editPost(
            @PathVariable Long id,
            @RequestBody PostsDto postsDto) throws CustomException {
        postsDto.setId(id);
        postService.editPost(postsDto);
        return new ResponseEntity<>("update successful", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id
    ) throws CustomException {
        PostsDto postsDto = new PostsDto();
        postsDto.setId(id);
        postService.deletePost(postsDto);
        return new ResponseEntity<>("Delete Successful", HttpStatus.ACCEPTED);

    }
    @PostMapping("/createPost")
    public ResponseEntity<String> CreatePost(
            @Valid @RequestBody PostsDto postsDto,
            Authentication authentication
    ) throws CustomException {
        UserDetails userDetails1 = (UserDetails) authentication.getPrincipal();
        PostsDto posts = postService.createPost(postsDto, userDetails1.getUsername());
        return new ResponseEntity<>(posts.getTitle(), HttpStatus.CREATED);
    }
}
