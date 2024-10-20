package com.JobAppBackend.JobFreakBackend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplyJobResponse {

    private Long applicationId;
    private String username;
    private Long jobId;
    private String applyLink;
}
