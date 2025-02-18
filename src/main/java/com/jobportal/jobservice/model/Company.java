package com.jobportal.jobservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="company", schema = "company")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Long id;
    private String companyName;
    private String companyDescription;
    private String yearFounded;
    private String logo;

    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    List<JobMatch> jobMatches;
}
