package com.jobportal.jobservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "question", schema = "job")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name="question_type")
    private String questionType;

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
    private JobPost jobPost;
}
