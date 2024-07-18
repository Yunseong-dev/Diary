package com.diary.auth.repository;

import com.diary.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByRefreshToken(String refreshToken);
}
