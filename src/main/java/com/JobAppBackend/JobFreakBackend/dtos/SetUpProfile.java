package com.JobAppBackend.JobFreakBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUpProfile {
    private String firstName;

    private String lastName;

    private String email;

    private String organization;

    private String resumeLink;

    private String contactNumber;

    private String passingOutYear;

    private String universityName;

    private String qualification;

    private String branch;
}
