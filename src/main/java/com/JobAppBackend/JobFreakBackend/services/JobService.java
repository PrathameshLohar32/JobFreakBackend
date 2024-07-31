package com.JobAppBackend.JobFreakBackend.services;

import com.JobAppBackend.JobFreakBackend.dtos.*;
import com.JobAppBackend.JobFreakBackend.entities.ApplicationEntity;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.entities.UserEntity;
import com.JobAppBackend.JobFreakBackend.repositories.ApplicationRepository;
import com.JobAppBackend.JobFreakBackend.repositories.JobsRepository;
import com.JobAppBackend.JobFreakBackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {
    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    ModelMapper modelMapper = new ModelMapper();


    public ResponseEntity<CreateJobDTO> postNewJob(CreateJobDTO createJobDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.getReferenceById(username);
        JobEntity newJob = modelMapper.map(createJobDTO,JobEntity.class);
        newJob.setPostedBy(username);
        JobEntity response = jobsRepository.save(newJob);

        List<Long> postedList = user.getAppliedJobs();
        if(postedList==null){
            postedList = new ArrayList<>();
        }
        postedList.add(response.getJobId());
        user.setPostedJobs(postedList);
        userRepository.save(user);
        CreateJobDTO postResponse = modelMapper.map(response,CreateJobDTO.class);
        return ResponseEntity.ok(postResponse);
    }

    public ResponseEntity<CreateJobDTO> getJobWithId(Long jobId) {
        JobEntity jobEntity = jobsRepository.getReferenceById(jobId);
        CreateJobDTO response = modelMapper.map(jobEntity,CreateJobDTO.class);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<CreateJobDTO>> getAllJobs() {
        List<JobEntity> allJobs = jobsRepository.findAll();
        List<CreateJobDTO> response = new ArrayList<>();
        for(JobEntity jobEntity : allJobs){
            CreateJobDTO responseElement = modelMapper.map(jobEntity, CreateJobDTO.class);
            response.add(responseElement);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CreateJobDTO> updateJob(UpdateJobRequest updateJobRequest, Long jobId){
        JobEntity jobEntity = jobsRepository.getReferenceById(jobId);
        if (updateJobRequest.getJobTitle() != null) {
            jobEntity.setJobTitle(updateJobRequest.getJobTitle());
        }
        if (updateJobRequest.getJobDescription() != null) {
            jobEntity.setJobDescription(updateJobRequest.getJobDescription());
        }
        if (updateJobRequest.getJobLocation() != null) {
            jobEntity.setJobLocation(updateJobRequest.getJobLocation());
        }
        if (updateJobRequest.getIsRemote() != null) {
            jobEntity.setIsRemote(updateJobRequest.getIsRemote());
        }
        if (updateJobRequest.getSalary() != null) {
            jobEntity.setSalary(updateJobRequest.getSalary());
        }
        if (updateJobRequest.getSalaryType() != null) {
            jobEntity.setSalaryType(updateJobRequest.getSalaryType());
        }
        if (updateJobRequest.getIsPartTime() != null) {
            jobEntity.setIsPartTime(updateJobRequest.getIsPartTime());
        }
        if (updateJobRequest.getIsIntership() != null) {
            jobEntity.setIsIntership(updateJobRequest.getIsIntership());
        }
        if (updateJobRequest.getJobCategory() != null) {
            jobEntity.setJobCategory(updateJobRequest.getJobCategory());
        }
        if (updateJobRequest.getIsActive() != null) {
            jobEntity.setIsActive(updateJobRequest.getIsActive());
        }
        if (updateJobRequest.getDateOfJoining() != null) {
            jobEntity.setDateOfJoining(updateJobRequest.getDateOfJoining());
        }
        if (updateJobRequest.getLastDateToApply() != null) {
            jobEntity.setLastDateToApply(updateJobRequest.getLastDateToApply());
        }

        // Save the updated entity
        jobEntity = jobsRepository.save(jobEntity);

        CreateJobDTO response = modelMapper.map(jobEntity, CreateJobDTO.class);

        return ResponseEntity.ok(response);

    }


    public ResponseEntity<Boolean> deleteJob(Long jobId) {
        JobEntity jobEntity = jobsRepository.getReferenceById(jobId);
        jobsRepository.delete(jobEntity);
        if(jobsRepository.existsById(jobId)){
            return ResponseEntity.internalServerError().body(false);
        }

        return ResponseEntity.ok(true);
    }

    public ResponseEntity<ApplyJobResponse> ApplyJob(ApplyJobRequest applyJobRequest, Long jobId) {
        JobEntity job = jobsRepository.getReferenceById(jobId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.getReferenceById(authentication.getName());
        if(job.getIsActive()){
            ApplicationEntity applicationEntity = modelMapper.map(applyJobRequest, ApplicationEntity.class);
            applicationEntity.setJobId(jobId);
            applicationEntity.setUsername(authentication.getName());
            applicationEntity.setAppliedDate(new Date());

            ApplicationEntity saved = applicationRepository.save(applicationEntity);

            ApplyJobResponse applyJobResponse = new ApplyJobResponse();
            applyJobResponse.setJobId(saved.getJobId());
            applyJobResponse.setApplicationId(saved.getApplicationId());
            applyJobResponse.setUsername(saved.getUsername());

            List<Long> appliedJobs = user.getAppliedJobs();
            if(appliedJobs==null){
                appliedJobs = new ArrayList<>();
            }
            appliedJobs.add(saved.getJobId());

            user.setAppliedJobs(appliedJobs);
            userRepository.save(user);

            Map<String,Long> applications = job.getApplications();
            if(applications==null){
                applications = new HashMap<>();
            }
            applications.put(saved.getUsername(), saved.getApplicationId());

            job.setApplications(applications);
            jobsRepository.save(job);

            return ResponseEntity.ok(applyJobResponse);

        }

        return ResponseEntity.badRequest().build();

    }

    public ResponseEntity<MyApplicationResponse> getMyApplication(Long jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobEntity jobEntity = jobsRepository.getReferenceById(jobId);
        String username = authentication.getName();
        Map<String,Long> applications = jobEntity.getApplications();
        if(applications!=null && applications.containsKey(username)){
            Long applicationId = applications.get(username);
            ApplicationEntity applicationEntity = applicationRepository.getReferenceById(applicationId);
            MyApplicationResponse response = modelMapper.map(applicationEntity, MyApplicationResponse.class);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<ApplicationEntity>> getAllApplications(Long jobId) {
        JobEntity jobEntity = jobsRepository.getReferenceById(jobId);
        Map<String,Long> applications = jobEntity.getApplications();
        Collection<Long> values = applications.values();
        List<Long> applicationIds = new ArrayList<>(values);

        List<ApplicationEntity> applicationEntityList = applicationRepository.findAllById(applicationIds);

        return ResponseEntity.ok(applicationEntityList);
    }
}
