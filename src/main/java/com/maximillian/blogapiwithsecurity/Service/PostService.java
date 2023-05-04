package com.maximillian.blogapiwithsecurity.Service;

import com.maximillian.blogapiwithsecurity.Dto.PostResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
     PostsDto createPost(PostsDto postsDto, String username) throws CustomException;
     List<PostsDto> displayAllPost();

     void deletePost(PostsDto postsDto) throws CustomException;

     void editPost(PostsDto postsDto) throws CustomException;

     List<PostsDto> searchPosts(String title);

    PostsDto viewPost(PostsDto postsDto) throws CustomException;

    PostResponse getAllPosts(Pageable pageable) throws CustomException;

}
