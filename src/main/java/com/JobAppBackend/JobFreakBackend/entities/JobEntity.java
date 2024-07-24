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
    private String companyName;

    @NonNull
    private String jobLocation;

    private boolean isRemote;

    @NonNull
    private Long salary;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private boolean isPartTime;

    private boolean isIntership;

    @NonNull
    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory;

    @NonNull
    private Date datePosted;

    private boolean isActive;

    @NonNull
    private String dateOfJoining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_id", nullable = false)
    private UserEntity postedBy;
}
