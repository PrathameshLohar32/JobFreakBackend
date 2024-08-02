package com.JobAppBackend.JobFreakBackend.dtos;

import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    @NonNull
    private String username;

    private UserType userType;

    private String organization;

    private String firstName;

    private String lastName;

    private String resumeLink;

    private Boolean isEmailVerified;

    private String passingOutYear;

    private String universityName;

    private String qualification;

    private String branch;
}
