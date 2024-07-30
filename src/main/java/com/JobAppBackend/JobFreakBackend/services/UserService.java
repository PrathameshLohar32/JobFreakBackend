package com.JobAppBackend.JobFreakBackend.services;


import com.JobAppBackend.JobFreakBackend.dtos.UserDTO;
import com.JobAppBackend.JobFreakBackend.dtos.UserProfileResponse;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.repositories.JobsRepository;
import com.JobAppBackend.JobFreakBackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JobsRepository jobsRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<UserProfileResponse> getUserProfile(String username) {
        UserEntity userEntity = userRepository.findById(username).get();
        UserProfileResponse userProfileResponse = modelMapper.map(userEntity,UserProfileResponse.class);

        return ResponseEntity.of(Optional.of(userProfileResponse));
    }

    public ResponseEntity<UserDTO> createUser(UserDTO createUserDTO) {
        UserEntity userEntity = modelMapper.map(createUserDTO, UserEntity.class);
        userRepository.save(userEntity);
        return ResponseEntity.of(Optional.of(createUserDTO));
    }


}
