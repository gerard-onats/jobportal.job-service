package com.jobportal.jobservice.request;

import org.springframework.lang.NonNull;

public class SearchBody {
    @NonNull
    private String query;
    private String location;
    private String jobType;
    private String datePosted;
    private String preference;
    private Experience experienceLevel;
    @NonNull
    private boolean fresh;
    @NonNull
    private Long pageNumber;

    public SearchBody() {}

    @NonNull
    public String getQuery() {
        return query;
    }

    public void setQuery(@NonNull String query) {
        this.query = query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public Experience getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(Experience experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    @NonNull
    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(@NonNull Long pageNumber) {
        this.pageNumber = pageNumber;
    }
}

enum Experience {
    INTERNSHIP,
    JUNIOR,
    SENIOR,
    PRINCIPAL;
}
