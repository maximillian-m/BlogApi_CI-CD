package com.maximillian.blogapiwithsecurity.Repositories;

import com.maximillian.blogapiwithsecurity.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = "select * from users where email = ?1", nativeQuery = true)
    Optional<Users>findByEmail(String email);
}
