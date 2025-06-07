package com.jobportal.jobservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="job_posts", schema = "job")
@Getter
@Setter
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_id")
    private Long id;

    @Column(name="job_title",
            nullable = false)
    private String jobTitle;

    @Column(name="job_description",
            nullable = false)
    private String jobDescription;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @Column(name="date_added")
    private Timestamp dateAdded;

    /*Warning - Can cause (n + 1) if invoked during a normal search query */
    @OneToMany(orphanRemoval = true, mappedBy = "jobPost")
    private List<Question> questions;
}
