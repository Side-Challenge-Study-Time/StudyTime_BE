package com.challenge.studytime.domain.refreshToken.repository;

import com.challenge.studytime.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenService extends JpaRepository<RefreshToken, Long> {
}
