package com.JobAppBackend.JobFreakBackend.entities;

import com.JobAppBackend.JobFreakBackend.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    private UserType userType;

    private String organization;

    private List<JobEntity> appliedJobs;

    private List<JobEntity> postedJobs;

    private String resumeLink;
}
