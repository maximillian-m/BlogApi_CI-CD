package com.maximillian.blogapiwithsecurity.Repositories;

import com.maximillian.blogapiwithsecurity.Models.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
