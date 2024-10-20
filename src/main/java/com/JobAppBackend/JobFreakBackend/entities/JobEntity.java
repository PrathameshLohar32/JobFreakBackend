package com.JobAppBackend.JobFreakBackend.entities;

import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import com.JobAppBackend.JobFreakBackend.enums.SalaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Table(name = "Jobs")
@NoArgsConstructor
@Entity
public class JobEntity {
    @Id
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

    private Boolean isRemote;

    @NonNull
    private Long salary;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private Boolean isPartTime;

    private Boolean isIntership;

    @NonNull
    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory;

    @NonNull
    private Date datePosted;

    private Boolean isActive;

    @NonNull
    private String dateOfJoining;

    @NonNull
    private String postedBy;

    private Date lastDateToApply;

    private String applyLink;

    @ElementCollection
    @MapKeyColumn(name = "username")
    @Column(name = "application_id")
    private Map<String, Long> applications;

}
