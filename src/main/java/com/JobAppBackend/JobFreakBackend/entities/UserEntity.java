package com.JobAppBackend.JobFreakBackend.entities;

import com.JobAppBackend.JobFreakBackend.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@Table(name = "User_entity")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean isEmailVerified;

    private String organization;

    private List<Long> postedJobs;

    private List<Long> appliedJobs;

    private String resumeLink;

    private String contactNumber;

    private String passingOutYear;

    private String universityName;

    private String qualification;

    private String branch;
}
