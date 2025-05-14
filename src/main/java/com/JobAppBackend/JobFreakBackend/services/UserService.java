package com.JobAppBackend.JobFreakBackend.services;


import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.entities.VerificationToken;
import com.JobAppBackend.JobFreakBackend.enums.UserType;
import com.JobAppBackend.JobFreakBackend.exceptions.ApiException;
import com.JobAppBackend.JobFreakBackend.exceptions.ResourceNotFoundException;
import com.JobAppBackend.JobFreakBackend.repositories.JobsRepository;
import com.JobAppBackend.JobFreakBackend.repositories.UserRepository;
import com.JobAppBackend.JobFreakBackend.repositories.VerificationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    VerificationTokenRepository tokenRepository;


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

       if(userRepository.existsById(createUserRequest.getUsername())){
           throw new ApiException("Username already exist please choose different username");
       }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        userEntity.setUserType(createUserRequest.getUserType());
        userEntity.setFirstName(createUserRequest.getFirstName());
        userEntity.setLastName(createUserRequest.getLastName());
        userEntity.setEmail(createUserRequest.getEmail());
        userEntity.setOrganization(createUserRequest.getOrganization());
        userEntity.setIsEmailVerified(false);
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
        List<JobEntity> response = new ArrayList<>();
        if(jobEntityList != null){
            response = jobsRepository.findAllById(jobEntityList);
        }
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
        if( jobEntityList==null){
            throw new ResourceNotFoundException("Jobs");
        }
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
            user.setEmail(setUpProfile.getEmail());
            user.setIsEmailVerified(false);
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

    public ResponseEntity<ApiResponse> sendVerificationLink(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }
        UserEntity user = userEntityOptional.get();
        if(user.getEmail()==null){
            throw new ApiException("Email not found. Please set your email");
        }
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiryTime(System.currentTimeMillis()+300000);
        tokenRepository.save(verificationToken);

        String url = String.format("http://localhost:8080/JobFreak/user/%s/verifyEmail?token=",username)+token;
        String body = "Click on Below link to verify your email\n";
        body = body+url;
        emailSenderService.sendEmail(user.getEmail(),"Link for JobFreak Verfication",body);
        return ResponseEntity.ok(new ApiResponse("Verfication Email sent", true));
    }

    public ResponseEntity<ApiResponse> verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return ResponseEntity.notFound().build();
        }
        if(verificationToken.getExpiryTime()<System.currentTimeMillis()){
            throw new ApiException("Verification Link Expired");
        }

        UserEntity user = verificationToken.getUser();
        user.setIsEmailVerified(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);

        return ResponseEntity.ok(new ApiResponse("Email verified Successfully",true));

    }

    public ResponseEntity<ApiResponse> changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if (userEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("user", "username", username);
        }
        if(!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmNewPassword())){
            return ResponseEntity.badRequest().body(new ApiResponse("new password and confirm password does not match",false));
        }

        UserEntity user = userEntityOptional.get();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new ApiException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        JdbcUserDetailsManager userDetailsManager=(JdbcUserDetailsManager) userDetailsService;

        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        UserDetails updatedUserDetails = User.withUserDetails(userDetails)
                .password(passwordEncoder.encode(changePasswordRequest.getNewPassword()))
                .build();
        userDetailsManager.updateUser(updatedUserDetails);

        return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
    }

    public ResponseEntity<ForgotPasswordResponse> sendForgotPasswordLink(ForgotPasswordRequest forgotPasswordRequest) {
        String emailOrUsername = forgotPasswordRequest.getEmailOrUsername();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpiryTime(System.currentTimeMillis() + 30000000);

        UserEntity user;
        if (emailOrUsername.endsWith(".com")) {
            user = userRepository.findByEmail(emailOrUsername);
            if (user == null || !Boolean.TRUE.equals(user.getIsEmailVerified())) {
                throw new ApiException("Enter a verified email.");
            }
        } else {
            Optional<UserEntity> userEntityOptional = userRepository.findById(emailOrUsername);
            if (userEntityOptional.isEmpty()) {
                throw new ApiException("Invalid username |");
            }
            user = userEntityOptional.get();
            if (!Boolean.TRUE.equals(user.getIsEmailVerified())) {
                throw new ApiException("Email is not verified.");
            }
        }

        VerificationToken token1 = tokenRepository.findByUser(user);
        if(token1 != null){

            tokenRepository.delete(token1);
        }

        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

        String url = "http://localhost:5173/forgotpassword/resetPassword?username=" + user.getUsername() + "&token=" + token;
        emailSenderService.sendEmail(user.getEmail(), "Reset password for JobFreak", "Click the link to reset your password:\n" + url);

        return ResponseEntity.ok(new ForgotPasswordResponse(user.getUsername(), token,"Reset password link is sent to verified mail"));
    }

    public ResponseEntity<VerifyTokenResponse> verifyResetToken(String token, String username) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryTime() < System.currentTimeMillis()) {
            return ResponseEntity.badRequest().body(new VerifyTokenResponse("Invalid or expired token", false,null));
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if (userEntityOptional.isEmpty() || !userEntityOptional.get().equals(verificationToken.getUser())) {
            return ResponseEntity.badRequest().body(new VerifyTokenResponse("Invalid token or username", false,null));
        }

        return ResponseEntity.ok(new VerifyTokenResponse("Token verified, proceed to reset password", true,token));
    }

    public ResponseEntity<ApiResponse> resetPassword(ResetPasswordRequest request) {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse("New password and confirm password do not match", false));
        }

        VerificationToken verificationToken = tokenRepository.findByToken(request.getToken());
        if (verificationToken == null || verificationToken.getExpiryTime() < System.currentTimeMillis()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid or expired token", false));
        }

        UserEntity user = verificationToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        JdbcUserDetailsManager userDetailsManager = (JdbcUserDetailsManager) userDetailsService;
        UserDetails userDetails = userDetailsManager.loadUserByUsername(user.getUsername());
        UserDetails updatedUserDetails = User.withUserDetails(userDetails)
                .password(passwordEncoder.encode(request.getNewPassword()))
                .build();
        userDetailsManager.updateUser(updatedUserDetails);

        return ResponseEntity.ok(new ApiResponse("Password reset successfully. Kindly log in with the new password", true));
    }

    public ResponseEntity<UserType> getRole(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new ResourceNotFoundException("user","username",username);
        }

        return ResponseEntity.ok(userEntityOptional.get().getUserType());
    }
}
