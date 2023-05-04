package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Dto.CommentResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Service.CommentsService;
import com.maximillian.blogapiwithsecurity.Service.PostService;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private UserService userService;
    private CommentsService commentsService;
    private PostService postService;

    public CommentController(UserService userService, CommentsService commentsService, PostService postService) {
        this.userService = userService;
        this.commentsService = commentsService;
        this.postService = postService;
    }


    //A create comment endpoint that take in the post id as a pathvariable
    @PostMapping("/create-comment/{id}")
    public ResponseEntity<String> createComment(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody CommentDto commentDto){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CommentDto commentDto1 = commentsService.createComment(commentDto, userDetails.getUsername(), id);
        return new ResponseEntity<>(commentDto1.getComments(), HttpStatus.CREATED);
    }

    //A delete comment endpoint that takes in the post Id as a pathvariable
    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<String> deleteComment(
            Authentication authentication,
            @PathVariable Long id,
            CommentDto commentDto) throws CustomException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentDto.setId(id);

        commentsService.deleteComment(commentDto.getId(), userDetails.getUsername());
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }


  //A put mapping that updates the comment if found.
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateComment(
          Authentication authentication,
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) throws CustomException {

        commentDto.setId(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       CommentDto comments = commentsService.UpdateComment(commentDto, userDetails.getUsername());
        return  new ResponseEntity<>(comments.getComments(), HttpStatus.OK);
    }


//An endpoint that views a single comment by id
    @GetMapping("/view/{id}")
    public ResponseEntity<CommentDto> viewComment(@PathVariable Long id, CommentDto commentDto) throws CustomException {
        commentDto.setId(id);
        CommentDto comment = commentsService.viewComment(commentDto);
        return new ResponseEntity<>(comment, HttpStatus.FOUND);
    }
//This is a search comment by keywords for a particular post which is represented by the id
    @GetMapping("/search/comments/{id}")
    public ResponseEntity<List<CommentDto>> searchComment(@PathVariable Long id,@RequestBody CommentDto commentDto) throws CustomException {
        PostsDto postsDto = new PostsDto();
        postsDto.setId(id);
        List<CommentDto> commentDto1 = commentsService.searchComment(postsDto, commentDto.getComments());
        return new ResponseEntity<>(commentDto1, HttpStatus.FOUND);
    }

    @GetMapping("/get-view-paginated/{postId}")
    public ResponseEntity<CommentResponse> getAllCommentForPage( @RequestParam(defaultValue = "0") int pageNum,
                                                                @PathVariable Long postId,
                                                                @RequestParam(defaultValue = "10") int pageSize){
        CommentResponse commentResponse = commentsService.findPage(postId, pageNum, pageSize);
        return ResponseEntity.ok(commentResponse);
    }
}
