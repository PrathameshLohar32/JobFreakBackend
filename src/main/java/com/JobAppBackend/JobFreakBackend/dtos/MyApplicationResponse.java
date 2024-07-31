package com.JobAppBackend.JobFreakBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyApplicationResponse {
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
