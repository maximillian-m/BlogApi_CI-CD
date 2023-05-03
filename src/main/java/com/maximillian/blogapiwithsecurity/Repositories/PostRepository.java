package com.maximillian.blogapiwithsecurity.Repositories;

import com.maximillian.blogapiwithsecurity.Models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Query(value = "update posts set content = ?1 and title = 2? where id = 3?", nativeQuery = true)
    public void updatePosts(String content, String url, Long id);

    @Query(value = "select * from posts where title like %?1%", nativeQuery = true)
    List<Posts> searchByTitle(String title);
}
