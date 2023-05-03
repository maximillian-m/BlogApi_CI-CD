package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.LikeDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    // The entity that handles the like and the unlike situation of the user
    @PostMapping("/Like/{id}/{likeId}")
    public ResponseEntity<String> likeAPost(@PathVariable Long id, @PathVariable Long likeId,  HttpServletRequest request) throws CustomException {
        HttpSession session = request.getSession();
        Long user_id = (Long) session.getAttribute("user_id");
        LikeDto likeDto = new LikeDto();
        likeDto.setPost_id(id);
        likeDto.setLikeId(likeId);
        likeDto.setUser_id(user_id);
        likeService.likeOrUnlikePost(likeDto);
        return new ResponseEntity<>("Liked post with id: " + id, HttpStatus.OK);
    }

}
