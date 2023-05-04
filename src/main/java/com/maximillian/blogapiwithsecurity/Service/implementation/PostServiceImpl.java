package com.maximillian.blogapiwithsecurity.Service.implementation;

import com.maximillian.blogapiwithsecurity.Dto.PostResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Enums.Roles;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Models.Posts;
import com.maximillian.blogapiwithsecurity.Models.Users;
import com.maximillian.blogapiwithsecurity.Repositories.PostRepository;
import com.maximillian.blogapiwithsecurity.Repositories.UserRepository;
import com.maximillian.blogapiwithsecurity.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maximillian.blogapiwithsecurity.Mapper.PostsMapper.mapDtoToPost;
import static com.maximillian.blogapiwithsecurity.Mapper.PostsMapper.mapPostToDto;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    //Creation of post
    @Override
    public PostsDto createPost(PostsDto postsDto, String request) throws CustomException {
        Posts posts = mapDtoToPost(postsDto);
        Users user = userRepository.findByEmail(request)
                .orElseThrow(() -> new CustomException("User not found"));
            posts.setUser(user);
            posts.setCreatedAt(LocalDateTime.now());
            posts.setUpdatedAt(LocalDateTime.now());
            postRepository.save(posts);
            return mapPostToDto(posts);
    }

    //Display of all post
    @Override
    public List<PostsDto> displayAllPost() {
        List<PostsDto> postDto = new ArrayList<>();
        List<Posts> posts = postRepository.findAll();
        for(Posts p : posts){
            postDto.add(mapPostToDto(p));
        }
        return postDto;
    }

    //Admin deleting a post
    @Override
    public void deletePost(PostsDto postDto) throws CustomException {

        Posts post = new Posts();
        post.setId(postDto.getId());
        try {
            postRepository.deleteById(post.getId());
        }catch(Exception e){
            throw new CustomException("No such post exist");
        }
    }


    // Method to update or edit a given post for an admin
    @Override
    @Transactional
    public void editPost(PostsDto postsDto) throws CustomException {
        Posts post = new Posts();
        post.setId(postsDto.getId());
        Posts post1 = postRepository.findById(post.getId())
                .orElseThrow(()-> new CustomException("Post doesn't exist"));
        post1.setContent(postsDto.getContent());
        post1.setTitle(postsDto.getTitle());
        post1.setUpdatedAt(LocalDateTime.now());
    }


    //Command to search for a post
    @Override
    public List<PostsDto> searchPosts(String title) {
        Posts post = new Posts();
        post.setTitle(title);
        List<Posts> posts = postRepository.searchByTitle(title);
        List<PostsDto> postsDto = new ArrayList<>();
        if(posts.isEmpty()){
            throw new RuntimeException("The searched post does not exist");
        }else{
            for (Posts p: posts) {
                postsDto.add(mapPostToDto(p));
            }
        }
        return postsDto;
    }


    //view for a single post
    @Override
    public PostsDto viewPost(PostsDto postsDto) throws CustomException {
        Posts post = postRepository.findById(postsDto.getId()).orElseThrow(() -> new CustomException("Post does not exist"));
        return mapPostToDto(post);
    }

    @Override
    public PostResponse getAllPosts(Pageable pageable) throws CustomException {
        Page<Posts> postsDtos = postRepository.findAll(pageable);
        if(!postsDtos.hasContent()){
            throw new CustomException("NO POST AVAILABLE");
        }
        List<Posts> postsList = postsDtos.getContent();
        List<PostsDto> postsDtoList = postsList.stream().map(posts -> mapPostToDto(posts)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setResponsePost(postsDtoList);
        postResponse.setLast(postResponse.isLast());
        postResponse.setFirst(postResponse.isFirst());
        postResponse.setPageNo(postResponse.getPageNo());
        postResponse.setPageSize(postResponse.getPageSize());
        postResponse.setTotalElement(postResponse.getTotalElement());
        postResponse.setTotalPage(postResponse.getTotalPage());
        return postResponse;
    }

}
