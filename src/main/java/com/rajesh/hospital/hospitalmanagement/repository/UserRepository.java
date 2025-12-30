package com.rajesh.hospital.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.hospital.hospitalmanagement.entity.User;
import com.rajesh.hospital.hospitalmanagement.entity.type.AuthProviderType;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String username);

Optional<User> findByProviderIdAndAuthProviderType(
            String providerId,
            AuthProviderType authProviderType
    );

}
