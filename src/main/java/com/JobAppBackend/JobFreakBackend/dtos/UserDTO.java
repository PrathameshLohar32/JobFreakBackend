package com.JobAppBackend.JobFreakBackend.dtos;

import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NonNull
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
