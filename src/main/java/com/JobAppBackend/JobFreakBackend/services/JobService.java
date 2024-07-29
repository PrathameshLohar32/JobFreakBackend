package com.JobAppBackend.JobFreakBackend.services;

import com.JobAppBackend.JobFreakBackend.dtos.CreateJobDTO;
import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.repositories.JobsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    JobsRepository jobsRepository;

    ModelMapper modelMapper = new ModelMapper();


    public ResponseEntity<CreateJobDTO> postNewJob(CreateJobDTO createJobDTO) {
        JobEntity newJob = modelMapper.map(createJobDTO,JobEntity.class);
        JobEntity response = jobsRepository.save(newJob);
        CreateJobDTO postResponse = modelMapper.map(response,CreateJobDTO.class);
        return ResponseEntity.ok(postResponse);
    }

}
