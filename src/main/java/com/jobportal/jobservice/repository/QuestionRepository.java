package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT q FROM Question q WHERE q.jobId = :id")
    public List<Question> findQuestionById(@Param("id") Long id);
}
