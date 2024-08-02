package com.JobAppBackend.JobFreakBackend.dtos;

import com.JobAppBackend.JobFreakBackend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private UserType userType;

    private String email;

    private String firstName;

    private String lastName;

    private String organization;

}
