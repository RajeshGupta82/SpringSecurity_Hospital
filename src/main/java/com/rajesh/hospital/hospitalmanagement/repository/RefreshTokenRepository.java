package com.rajesh.hospital.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.hospital.hospitalmanagement.entity.RefreshToken;
import com.rajesh.hospital.hospitalmanagement.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

Optional<RefreshToken> findByToken(String token);

void deleteByUser(User user);
}
