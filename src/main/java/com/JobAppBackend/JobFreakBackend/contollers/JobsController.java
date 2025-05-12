package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.ApplicationEntity;
import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import com.JobAppBackend.JobFreakBackend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("JobFreak/jobs")
public class JobsController {

    @Autowired
    JobService jobService;

//    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/post")
    public ResponseEntity<CreateJobDTO> postNewJob(@RequestBody CreateJobDTO createJobDTO){
        return jobService.postNewJob(createJobDTO);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<CreateJobDTO> getJobWithJobId(@PathVariable Long jobId){
        return jobService.getJobWithId(jobId);
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{jobId}")
    public ResponseEntity<CreateJobDTO> updateJob(@RequestBody UpdateJobRequest updateJobRequest,@PathVariable Long jobId){
        return jobService.updateJob(updateJobRequest,jobId);
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Boolean> deleteJob(@PathVariable Long jobId){
        return jobService.deleteJob(jobId);
    }

//    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/{jobId}/apply")
    public Object applyToJob(@RequestBody(required = false) ApplyJobRequest applyJobRequest, @PathVariable Long jobId, @RequestParam(required = false) boolean applyWithProfile){
        var response = jobService.ApplyJob(applyJobRequest, jobId, applyWithProfile);
        var body = response.getBody();
        if (body != null && body.getApplyLink() != null) {
            return handleExternalJob(body.getApplyLink());
        }
        return response;
    }

    public RedirectView handleExternalJob(String applyLink){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(applyLink);
        return redirectView;
    }

//    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/{jobId}/myApplication")
    ResponseEntity<MyApplicationResponse> getMyApplication(@PathVariable Long jobId){
        return jobService.getMyApplication(jobId);
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/{jobId}/allApplications")
    ResponseEntity<List<ApplicationEntity>> getAllApplications(@PathVariable Long jobId){
        return jobService.getAllApplications(jobId);
    }

//    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/{jobId}/myApplication")
    ResponseEntity<Boolean> withDraw(@PathVariable Long jobId){
        return jobService.withDrawMyApplication(jobId);
    }

    @GetMapping()
    public ResponseEntity<List<CreateJobDTO>> getAllJobsWithSortingAndFiltering(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "datePosted") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(required = false) Boolean isRemote,
            @RequestParam(required = false) Boolean isPartTime,
            @RequestParam(required = false) Boolean isIntership,
            @RequestParam(required = false) JobCategory jobCategory,
            @RequestParam(required = false) Boolean isActive) {


        return jobService.getAllJobsWithSorting(page,size,sortBy,direction,isRemote,isPartTime,isIntership,jobCategory,isActive);
    }


}
