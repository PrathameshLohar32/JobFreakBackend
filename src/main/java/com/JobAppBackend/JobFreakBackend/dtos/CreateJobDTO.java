package com.JobAppBackend.JobFreakBackend.dtos;

import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import com.JobAppBackend.JobFreakBackend.enums.SalaryType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreateJobDTO {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @NonNull
    private String jobTitle;

    @NonNull
    private String jobDescription;

    @NonNull
    private String companyName;

    @NonNull
    private String jobLocation;

    private boolean isRemote;

    @NonNull
    private Long salary;


    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private boolean isPartTime;

    private boolean isIntership;


    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory;


    private Date datePosted;

    private boolean isActive;


    private String dateOfJoining;

    private String postedBy;
}
