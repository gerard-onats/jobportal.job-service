package com.jobportal.jobservice.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class SearchJobsRequest {
//    @NonNull
    private String query;
//    @Nullable
    private String location;
    private String jobType;
    private String datePosted;
    private String preference;
    private String experienceLevel;
//    @NonNull
    private Boolean fresh;
//    @NonNull
    private Long pageNumber;
}
