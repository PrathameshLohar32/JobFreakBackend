package com.JobAppBackend.JobFreakBackend.services;


import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.exceptions.ResourceNotFoundException;
import com.JobAppBackend.JobFreakBackend.repositories.JobsRepository;
import com.JobAppBackend.JobFreakBackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<UserProfileResponse> getUserProfile(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }
        UserEntity userEntity = userEntityOptional.get();
        UserProfileResponse userProfileResponse = modelMapper.map(userEntity,UserProfileResponse.class);

        return ResponseEntity.of(Optional.of(userProfileResponse));
    }

    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        userEntity.setUserType(createUserRequest.getUserType());
        userEntity.setFirstName(createUserRequest.getFirstName());
        userEntity.setLastName(createUserRequest.getLastName());
        userEntity.setEmail(createUserRequest.getEmail());
        userEntity.setOrganization(createUserRequest.getOrganization());
        userRepository.save(userEntity);
        CreateUserResponse response = modelMapper.map(userEntity, CreateUserResponse.class);
        return ResponseEntity.of(Optional.of(response));
    }


    public ResponseEntity<List<JobEntity>> getAppliedJobs(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }
        UserEntity user = userEntityOptional.get();

        List<Long> jobEntityList = user.getAppliedJobs();
        List<JobEntity> response = jobsRepository.findAllById(jobEntityList);
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Jobs");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<JobEntity>> getPostedJobs(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }
        UserEntity user = userEntityOptional.get();
        List<Long> jobEntityList = user.getPostedJobs();
        List<JobEntity> response = jobsRepository.findAllById(jobEntityList);
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Jobs");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse> setUpUserProfile(SetUpProfile setUpProfile,String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }
        UserEntity user = userEntityOptional.get();
        if(setUpProfile.getFirstName()!=null){
            user.setFirstName(setUpProfile.getFirstName());
        }
        if(setUpProfile.getLastName()!=null){
            user.setLastName(setUpProfile.getLastName());
        }
        if(setUpProfile.getEmail()!=null){
            //Todo : verify new email.
            user.setEmail(setUpProfile.getEmail());
        }
        if(setUpProfile.getOrganization()!=null){
            user.setOrganization(setUpProfile.getOrganization());
        }
        if(setUpProfile.getContactNumber()!=null){
            user.setContactNumber(setUpProfile.getContactNumber());
        }
        if(setUpProfile.getUniversityName()!=null){
            user.setUniversityName(setUpProfile.getUniversityName());
        }
        if(setUpProfile.getQualification()!=null){
            user.setQualification(setUpProfile.getQualification());
        }
        if(setUpProfile.getBranch()!=null){
            user.setBranch(setUpProfile.getBranch());
        }
        if(setUpProfile.getPassingOutYear()!=null){
            user.setPassingOutYear(setUpProfile.getPassingOutYear());
        }
        if(setUpProfile.getResumeLink()!=null){
            user.setResumeLink(setUpProfile.getResumeLink());
        }

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse("Profile set up completed",true));

    }
}
