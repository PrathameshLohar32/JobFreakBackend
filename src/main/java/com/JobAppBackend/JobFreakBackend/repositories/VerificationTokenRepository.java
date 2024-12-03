package com.JobAppBackend.JobFreakBackend.repositories;

import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(UserEntity user);
}
