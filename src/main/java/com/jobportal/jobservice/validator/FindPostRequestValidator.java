package com.jobportal.jobservice.validator;

import com.jobportal.jobservice.request.SearchJobsRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class FindPostRequestValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return SearchJobsRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SearchJobsRequest request = (SearchJobsRequest) target;
        
    }
}
