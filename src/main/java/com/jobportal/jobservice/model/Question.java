package com.jobportal.jobservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "question", schema = "job")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="question_type")
    private QuestionType questionType;

    @Column(name="expected_answer",
            nullable = false)
    private String expectedAnswer;

    @Column(name="date_added")
    private Date dateAdded;

    @Column(name="date_updated")
    private Date dateUpdated;

    @JsonIgnore
    @Column(name="job_id")
    private Long jobId;

    @JsonIgnore
    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",
            insertable = false,
            updatable = false)
    private JobMatch jobMatch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public JobMatch getJobMatch() {
        return jobMatch;
    }

    public void setJobMatch(JobMatch jobMatch) {
        this.jobMatch = jobMatch;
    }

    enum QuestionType {
        NUMERIC, YES_NO, INTEGER
    }
}
