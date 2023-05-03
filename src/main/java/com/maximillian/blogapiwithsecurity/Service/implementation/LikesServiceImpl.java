package com.maximillian.blogapiwithsecurity.Service.implementation;

import com.maximillian.blogapiwithsecurity.Dto.LikeDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Models.Likes;
import com.maximillian.blogapiwithsecurity.Models.Posts;
import com.maximillian.blogapiwithsecurity.Models.Users;
import com.maximillian.blogapiwithsecurity.Repositories.LikesRepository;
import com.maximillian.blogapiwithsecurity.Repositories.PostRepository;
import com.maximillian.blogapiwithsecurity.Repositories.UserRepository;
import com.maximillian.blogapiwithsecurity.Service.LikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikesServiceImpl implements LikeService {
    private LikesRepository likesRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    public LikesServiceImpl(LikesRepository likesRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
// this is a helper method to use to help the like or unlike method
    @Override
    public void checkLike(LikeDto likeDto) throws CustomException {
        Users user = userRepository.findById(likeDto.getUser_id()).orElseThrow(() -> new CustomException("Login to be able to like post"));
        Posts post = postRepository.findById(likeDto.getPost_id()).orElseThrow(() -> new CustomException("post does not exist"));
        Likes likes = new Likes();
        likes.setUpdatedAt(LocalDateTime.now());
        likes.setCreatedAt(LocalDateTime.now());
        likes.setPost(post);
        likes.setUser(user);
        likesRepository.save(likes);
    }

    //This is the method that checks if a post has been liked already by a user. if it has then unlike otherwise create a like dor that post
    @Override
    public void likeOrUnlikePost(LikeDto likeDto) throws CustomException {
       Optional<Likes> likes = likesRepository.findById(likeDto.getLikeId());
        if(likes.isPresent()){
            Likes likes1 = likes.get();
            likes1.setIsLiked(false);
            likes1.setUpdatedAt(LocalDateTime.now());
            likesRepository.save(likes1);
        }else if(!likes.isPresent()){
            checkLike(likeDto);
        }else{
            throw new CustomException("You cannot dislike again");
        }
    }
}
