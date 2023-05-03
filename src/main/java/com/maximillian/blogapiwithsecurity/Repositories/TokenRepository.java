package com.maximillian.blogapiwithsecurity.Repositories;

import com.maximillian.blogapiwithsecurity.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(
            value = "select * from token where user_id = ?1 and (is_expired = false or is_revoked = false)",
            nativeQuery = true
    )
    List<Token> findAllTokenByUser(Long id);
}
