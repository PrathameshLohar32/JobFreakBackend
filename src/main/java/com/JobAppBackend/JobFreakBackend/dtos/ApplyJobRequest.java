package com.JobAppBackend.JobFreakBackend.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApplyJobRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String coverLetter;

    private String resumeLink;

    private String passingOutYear;

    private String universityName;

    private String qualification;

    private String branch;
}
