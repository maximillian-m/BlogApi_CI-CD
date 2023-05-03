package com.maximillian.blogapiwithsecurity.Mapper;

import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Models.Posts;

public class PostsMapper {
    public static PostsDto mapPostToDto(Posts post){
        PostsDto postDto = new PostsDto();
        postDto.setCategory(post.getCategory());
        postDto.setContent(post.getContent());
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setImage_url(post.getImage_url());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        return postDto;
    }

    public static Posts mapDtoToPost(PostsDto postsDto){
        Posts posts = new Posts();
        posts.setCategory(postsDto.getCategory());
        posts.setContent(postsDto.getContent());
        posts.setId(postsDto.getId());
        posts.setTitle(postsDto.getTitle());
        posts.setImage_url(postsDto.getImage_url());
        return posts;
    }
}
