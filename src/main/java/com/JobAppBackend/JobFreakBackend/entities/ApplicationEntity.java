package com.JobAppBackend.JobFreakBackend.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "applications")
@NoArgsConstructor
@Entity
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId", unique = true, nullable = false)
    private Long applicationId;

    private Long jobId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String coverLetter;

    private Date appliedDate;

    private String resumeLink;

    private String passingOutYear;

    private String universityName;

    private String qualification;

    private String branch;
}
