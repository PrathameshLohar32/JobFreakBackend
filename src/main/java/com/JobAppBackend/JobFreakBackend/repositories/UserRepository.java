package com.JobAppBackend.JobFreakBackend.repositories;

import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
}
