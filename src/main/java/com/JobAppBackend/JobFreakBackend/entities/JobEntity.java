package com.JobAppBackend.JobFreakBackend.entities;

import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import com.JobAppBackend.JobFreakBackend.enums.SalaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@Table(name = "Jobs")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobId", unique = true, nullable = false)
    private Long jobId;

    @NonNull
    @Column(nullable = false)
    private String jobTitle;

    @NonNull
    @Column(nullable = false)
    private String jobDescription;

    @NonNull
    private String CompanyName;

    @NonNull
    private String jobLocation;

    @NonNull
    private boolean isRemote;

    @NonNull
    private Long salary;

    @NonNull
    private SalaryType salaryType;

    @NonNull
    private boolean isPartTime;

    @NonNull
    private boolean isIntership;

    @NonNull
    private JobCategory jobCategory;

    @NonNull
    private Date datePosted;

    @NonNull
    private boolean isActive;

    @NonNull
    private String dateOfJoining;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserEntity postedBy;



}
