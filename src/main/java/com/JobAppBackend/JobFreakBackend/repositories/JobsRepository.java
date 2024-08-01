package com.JobAppBackend.JobFreakBackend.repositories;

import com.JobAppBackend.JobFreakBackend.entities.JobEntity;
import com.JobAppBackend.JobFreakBackend.enums.JobCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<JobEntity,Long> {
    @Query("SELECT j FROM JobEntity j WHERE " +
            "(:isRemote IS NULL OR j.isRemote = :isRemote) AND " +
            "(:isPartTime IS NULL OR j.isPartTime = :isPartTime) AND " +
            "(:isIntership IS NULL OR j.isIntership = :isIntership) AND " +
            "(:jobCategory IS NULL OR j.jobCategory = :jobCategory) AND " +
            "(:isActive IS NULL OR j.isActive = :isActive)")
    Page<JobEntity> findByFilters(
            @Param("isRemote") Boolean isRemote,
            @Param("isPartTime") Boolean isPartTime,
            @Param("isIntership") Boolean isIntership,
            @Param("jobCategory") JobCategory jobCategory,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
