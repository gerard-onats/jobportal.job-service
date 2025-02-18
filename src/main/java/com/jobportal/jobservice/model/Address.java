package com.jobportal.jobservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="company_address", schema = "company")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Company company;
    private String city;
    private String country;
    private String companyState;
    private String zipCode;
}
