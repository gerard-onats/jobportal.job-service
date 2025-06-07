package com.jobportal.jobservice.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobportal.jobservice.DTO.JobMatchDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchJobResponse {
//    @JsonInclude()
//    private Long pages;
//    @JsonInclude()
//    private Long matches;
    private List<JobMatchDTO> payload;

    @JsonIgnore
    private boolean includeMatches;
    @JsonIgnore
    private boolean includePages;
}
