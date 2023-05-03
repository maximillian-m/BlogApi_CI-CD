package com.maximillian.blogapiwithsecurity.Repositories;

import com.maximillian.blogapiwithsecurity.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    @Query(value= "select * from comments where post_id = ?1 and comments like %?2%", nativeQuery = true)
    List<Comments> findByPostAndValue(Long id, String comments);
    @Query(value="Select * from comments where post_id = ?1", nativeQuery = true)
    List<Comments> findByPostId(Long postId);
}
