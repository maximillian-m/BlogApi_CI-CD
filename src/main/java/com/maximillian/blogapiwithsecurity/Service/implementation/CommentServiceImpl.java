package com.maximillian.blogapiwithsecurity.Service.implementation;

import com.maximillian.blogapiwithsecurity.Dto.CommentDto;
import com.maximillian.blogapiwithsecurity.Dto.CommentResponse;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Models.Comments;
import com.maximillian.blogapiwithsecurity.Models.Posts;
import com.maximillian.blogapiwithsecurity.Models.Users;
import com.maximillian.blogapiwithsecurity.Repositories.CommentsRepository;
import com.maximillian.blogapiwithsecurity.Repositories.PostRepository;
import com.maximillian.blogapiwithsecurity.Repositories.UserRepository;
import com.maximillian.blogapiwithsecurity.Service.CommentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maximillian.blogapiwithsecurity.Mapper.CommentsMapper.Dtotocomments;
import static com.maximillian.blogapiwithsecurity.Mapper.CommentsMapper.commentsToDto;

@Service
public class CommentServiceImpl implements CommentsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    public CommentServiceImpl(UserRepository userRepository, PostRepository postRepository, CommentsRepository commentsRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }


    //Creating comments
    @Override
    public CommentDto createComment(CommentDto commentDto, Long userId, Long id) {
        Users users = userRepository.findById(userId).get();
        Comments comments = Dtotocomments(commentDto);
        Posts post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("post not found"));
        comments.setUser(users);
        comments.setPost(post);
        comments.setCreatedAt(LocalDateTime.now());
        comments.setUpdatedAt(LocalDateTime.now());
        commentsRepository.save(comments);
        CommentDto commentDto1 = commentsToDto(comments);
        return commentDto1;
    }

    //The delete comment method
    @Override
    public void deleteComment(Long id, Long userId) throws CustomException {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new CustomException("Comment not found"));
        if(!comments.getUser().getId().equals(userId)){
            throw new CustomException("you are not authorized to delete a comment that is not yours");
        }
        commentsRepository.deleteById(id);
    }


    //The update comment session
    @Override
    public CommentDto UpdateComment(CommentDto commentDto, Long userId) throws CustomException {
        Comments comments = commentsRepository.findById(commentDto.getId()).orElseThrow(() -> new CustomException("Comment not found"));
        if(!comments.getUser().getId().equals(userId)){
            throw new CustomException("you are not authorized to delete a comment that is not yours");
        }
        comments.setComments(commentDto.getComments());
        comments.setUpdatedAt(LocalDateTime.now());
        commentsRepository.save(comments);
        return commentsToDto(comments);
    }
// This views a single comment
    @Override
    public CommentDto viewComment(CommentDto commentDto) throws CustomException {
        Comments comments = commentsRepository.findById(commentDto.getId()).orElseThrow(() -> new CustomException("comment is not available"));
        return commentsToDto(comments);
    }

//This is used to search a comment
    @Override
    public List<CommentDto> searchComment(PostsDto postsDto, String typed) throws CustomException {
        List<Comments> comments = commentsRepository.findByPostAndValue(postsDto.getId(), typed);
        List<CommentDto> commentDtos = new ArrayList<>();
        if(comments.isEmpty()){
            throw new CustomException("No such comment exists");
        }
        for(Comments c: comments){
            commentDtos.add(commentsToDto(c));
        }
        return commentDtos;
    }

    //This is a commentResponse that was created to house the fields for a page to be displayed.
    //1. I found the comment by the postid that is passed as a pathvariable from the controller
    //2. The Pagesize can be set to a default page size but it is also passed from the controller
    // as well as the current page number
    //After the retrieving using the pageImpl interface the commentDto is used to sent the data to the commentResponse
    @Override
    public CommentResponse findPage(Long postId, int pageNum, int pageSize) {
        List<Comments> comments = commentsRepository.findByPostId(postId);
        pageSize =5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Comments> pagedComments = new PageImpl<>(comments, pageable, comments.size());
        List<Comments> listOfComments =pagedComments.getContent();

        List<CommentDto> commentDto = listOfComments.stream().map(comment -> commentsToDto(comment)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContentList(commentDto);
        commentResponse.setLast(pagedComments.isLast());
        commentResponse.setFirst(pagedComments.isFirst());
        commentResponse.setTotalElements(pagedComments.getNumberOfElements());
        commentResponse.setTotalPages(pagedComments.getTotalPages());
        commentResponse.setPageNo(pagedComments.getNumber());
        commentResponse.setPageSize(pagedComments.getSize());
        return commentResponse;
    }
}

