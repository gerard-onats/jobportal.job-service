package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.model.JobMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMatchRepository extends JpaRepository<JobMatch, Long>, JpaSpecificationExecutor<JobMatch> {
}
