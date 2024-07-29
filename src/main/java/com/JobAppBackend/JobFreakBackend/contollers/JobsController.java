package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.dtos.CreateJobDTO;
import com.JobAppBackend.JobFreakBackend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("JobFreak/jobs")
public class JobsController {

    @Autowired
    JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<CreateJobDTO> postNewJob(@RequestBody CreateJobDTO createJobDTO){
        return jobService.postNewJob(createJobDTO);
    }
}
