package com.JobAppBackend.JobFreakBackend.entities;

import com.JobAppBackend.JobFreakBackend.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String organization;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobEntity> postedJobs;

    @ManyToMany
    @JoinTable(
            name = "user_applied_jobs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<JobEntity> appliedJobs;

    private String resumeLink;
}
