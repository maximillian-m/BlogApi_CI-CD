package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.PostResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Service.PostService;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
   private PostService postService;
   private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<String> CreatePost(
            @Valid @RequestBody PostsDto postsDto,
            HttpServletRequest request) throws CustomException {
        HttpSession session = request.getSession();
        Long id = (Long)session.getAttribute("admin_id");
        PostsDto posts = postService.createPost(postsDto, id);
        return new ResponseEntity<>(posts.getTitle(), HttpStatus.CREATED);
    }
    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostsDto>> getAllPost(){
        List<PostsDto> returnedPosts = postService.displayAllPost();
        return new ResponseEntity<>(returnedPosts, HttpStatus.OK);
}

@DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @RequestBody PostsDto postsDto, HttpServletRequest httpServletRequest) throws CustomException {
        HttpSession session = httpServletRequest.getSession();
        Long adminId = (Long) session.getAttribute("admin_id");
        postsDto.setId(id);
        postService.deletePost(postsDto, adminId);
        return new ResponseEntity<>("Delete Successful", HttpStatus.ACCEPTED);

    }
    @PutMapping("/edit-post/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id, HttpServletRequest httpServletRequest,@RequestBody PostsDto postsDto) throws CustomException {
        HttpSession session = httpServletRequest.getSession();
        Long adminId = (Long) session.getAttribute("admin_id");
        postsDto.setId(id);
        postService.editPost(postsDto,adminId);
        return new ResponseEntity<>("update successful", HttpStatus.ACCEPTED);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PostsDto>> searchForPosts(@RequestBody PostsDto postsDto){
        List<PostsDto> theListOfSearches = postService.searchPosts(postsDto.getTitle());
        return new ResponseEntity<>(theListOfSearches, HttpStatus.FOUND);
    }

    @GetMapping("/get-post/{id}")
    public ResponseEntity<PostsDto> viewPost(@PathVariable Long id, PostsDto postsDto) throws CustomException {
        postsDto.setId(id);
        PostsDto postDto1 = postService.viewPost(postsDto);
        return new ResponseEntity<>(postDto1, HttpStatus.FOUND);
    }
    @GetMapping("/viewPost")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(defaultValue = "1") int pageNo,
                                           @RequestParam(defaultValue = "5") int pageSize) throws CustomException {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        PostResponse postsPage = postService.getAllPosts(pageable);
        return ResponseEntity.ok(postsPage);
    }
}
