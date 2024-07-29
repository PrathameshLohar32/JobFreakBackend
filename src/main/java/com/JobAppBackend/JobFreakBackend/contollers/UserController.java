package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.dtos.UserDTO;
import com.JobAppBackend.JobFreakBackend.dtos.UserProfileResponse;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("JobFreak/user")
public class UserController {

    @Autowired
    UserService userService;

//    @PostMapping("")
//    ResponseEntity<?>signUpUser(){
//        // Todo : Implement this after security configuration
//        return null;
//    }
    @PostMapping("/login")
    ResponseEntity<?>login(){
        // Todo : Implement this after security configuration
        return null;
    }

    @PostMapping("")
    ResponseEntity<UserDTO>createUser(@RequestBody UserDTO createUserDTO){
        return userService.createUser(createUserDTO);
    }

    @GetMapping("/{username}")
    ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String username){
        return userService.getUserProfile(username);
    }

    @GetMapping("/{username}/appliedJobs")
    ResponseEntity<List<JobEntity>> getAppliedJobs(@PathVariable String username){
        return userService.getAppliedJobs(username);
    }





}
