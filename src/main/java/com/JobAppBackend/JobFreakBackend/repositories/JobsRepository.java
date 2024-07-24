package com.JobAppBackend.JobFreakBackend.repositories;

import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<JobEntity,Long> {
}
