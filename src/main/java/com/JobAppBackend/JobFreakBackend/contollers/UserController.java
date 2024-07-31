package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.security.jwt.JwtUtils;
import com.JobAppBackend.JobFreakBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("JobFreak/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;

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



    @PostMapping
    ResponseEntity<CreateUserResponse>createUser(@RequestBody CreateUserRequest request){
        UserDetails user1 = User.withUsername(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("USER")
                .build();
        JdbcUserDetailsManager userDetailsManager=(JdbcUserDetailsManager) userDetailsService;
        userDetailsManager.createUser(user1);
        return userService.createUser(request);
    }

    @GetMapping("/{username}")
    ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String username){
        return userService.getUserProfile(username);
    }

    @GetMapping("/{username}/appliedJobs")
    ResponseEntity<List<JobEntity>> getAppliedJobs(@PathVariable String username){
        return userService.getAppliedJobs(username);
    }

    @GetMapping("/{username}/postedJobs")
    ResponseEntity<List<JobEntity>> getPostedJobs(@PathVariable String username){
        return userService.getPostedJobs(username);
    }




    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken);

        return ResponseEntity.ok(response);
    }




}