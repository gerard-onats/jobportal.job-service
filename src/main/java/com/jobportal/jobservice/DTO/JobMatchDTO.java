package com.jobportal.jobservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JobMatchDTO implements Serializable {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String description;
    private String companyDescription;
    private String timePosted;
    private String base64Image;

    /*TODO: Add list for (Skills) */
}
