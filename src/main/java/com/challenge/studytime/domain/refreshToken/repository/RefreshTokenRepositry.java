package com.challenge.studytime.domain.refreshToken.repository;

import com.challenge.studytime.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepositry extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByValue(String value);
}
