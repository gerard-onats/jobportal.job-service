package com.jobportal.jobservice.request;

import org.springframework.lang.NonNull;

public class SearchBody {
    @NonNull
    private String query;
    private String location;
    private String jobType;
    private String datePosted;
    private String preference;
    private String experienceLevel;
    @NonNull
    private boolean isNewSearch;
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

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public boolean isNewSearch() {
        return isNewSearch;
    }

    public void setNewSearch(boolean newSearch) {
        isNewSearch = newSearch;
    }

    @NonNull
    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(@NonNull Long pageNumber) {
        this.pageNumber = pageNumber;
    }
}
