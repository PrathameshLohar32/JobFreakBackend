package com.JobAppBackend.JobFreakBackend.dtos;

import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import com.JobAppBackend.JobFreakBackend.enums.SalaryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class UpdateJobRequest {
    private String jobTitle;

    private String jobDescription;

    private String jobLocation;

    private Boolean isRemote;

    private Long salary;


    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private Boolean isPartTime;

    private Boolean isIntership;


    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory;

    private Boolean isActive;


    private String dateOfJoining;

    private Date lastDateToApply;
}
