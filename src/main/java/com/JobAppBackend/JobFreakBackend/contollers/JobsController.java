package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.ApplicationEntity;
import com.JobAppBackend.JobFreakBackend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("JobFreak/jobs")
public class JobsController {

    @Autowired
    JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<CreateJobDTO> postNewJob(@RequestBody CreateJobDTO createJobDTO){
        return jobService.postNewJob(createJobDTO);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<CreateJobDTO> getJobWithJobId(@PathVariable Long jobId){
        return jobService.getJobWithId(jobId);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<CreateJobDTO> updateJob(@RequestBody UpdateJobRequest updateJobRequest,@PathVariable Long jobId){
        return jobService.updateJob(updateJobRequest,jobId);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Boolean> deleteJob(@PathVariable Long jobId){
        return jobService.deleteJob(jobId);
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApplyJobResponse> applyToJob(@RequestBody ApplyJobRequest applyJobRequest, @PathVariable Long jobId){
        return jobService.ApplyJob(applyJobRequest,jobId);
    }

    @GetMapping("/{jobId}/myApplication")
    ResponseEntity<MyApplicationResponse> getMyApplication(@PathVariable Long jobId){
        return jobService.getMyApplication(jobId);
    }

    @GetMapping("/{jobId}/allApplications")
    ResponseEntity<List<ApplicationEntity>> getAllApplications(@PathVariable Long jobId){
        return jobService.getAllApplications(jobId);
    }

    @GetMapping()
    public ResponseEntity<List<CreateJobDTO>> getAllJobs(){
        return jobService.getAllJobs();
    }



}
