package com.jobportal.jobservice.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobMatchDTO implements Serializable {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String description;
    private String companyDescription;
    private String timePosted;
    private String encodedImage;

    /*TODO: Add list for (Skills) */
}
