package com.jobportal.jobservice.service;

import com.jobportal.jobservice.DTO.JobMatchDTO;
import com.jobportal.jobservice.constants.Constants;
import com.jobportal.jobservice.model.Address;
import com.jobportal.jobservice.model.JobPost;
import com.jobportal.jobservice.repository.JobMatchRepository;
import com.jobportal.jobservice.request.SearchJobsRequest;
import com.jobportal.jobservice.response.SearchJobResponse;
import com.jobportal.jobservice.utils.FormatterUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import static com.jobportal.jobservice.repository.JobMatchRepository.Specifications.*;

@Service
public class PostService {
    @Autowired
    AmazonS3Service s3Service;
    @Autowired
    JobMatchRepository jobMatchRepository;

    @PersistenceContext
    EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public SearchJobResponse search(SearchJobsRequest request) {
        final String value = request.getQuery();
        final List<String> columns = List.of("jobTitle", "jobDescription");
        final List<JobPost> matches = jobMatchRepository.findAll(hasMatchInColumnsByValue(columns, value));

        final SearchJobResponse response = new SearchJobResponse();
        response.setPayload(buildFormattedDTO(matches));

        if(request.getFresh()) {
            response.setIncludePages(true);
            response.setIncludeMatches(true);


        }
        return response;
    }

    private List<JobMatchDTO> buildFormattedDTO(List<JobPost> query) {
        List<JobMatchDTO> result = new ArrayList<>();
        Map<String, String> exists = new HashMap<>(); //extra cache to prevent costly encoding

        /* TODO Retrieve image objects in S3, and process using CompletableFuture for larger batches */
        for(JobPost match : query) {
            JobMatchDTO matchDTO = new JobMatchDTO();

            matchDTO.setId(match.getId());
            matchDTO.setCompany(match.getCompany().getCompanyName());
            matchDTO.setDescription(match.getJobDescription());
            matchDTO.setTitle(match.getJobTitle());
            matchDTO.setCompanyDescription(match.getCompany().getCompanyDescription());

            String logo = match.getCompany().getLogo();
            if(exists.containsKey(logo)) matchDTO.setEncodedImage(exists.get(logo));
            else {
                String base64Image = FormatterUtils.base64LogoFormatter(logo, s3Service.retrieveToBase64(logo));
                matchDTO.setEncodedImage(base64Image);
                exists.put(logo, base64Image);
            }

            Date dateAdded = match.getDateAdded();
            matchDTO.setTimePosted(FormatterUtils.datePostedFormatter(dateAdded));

            Address matchAddress = match.getCompany().getAddress();
            matchDTO.setLocation(FormatterUtils.locationFormatter(matchAddress));

            result.add(matchDTO);
        }

        return Collections.unmodifiableList(result);
    }

    private Pair<String, String> buildQueries(SearchJobsRequest searchBody) {
        final String TABLE_COLUMNS = "job.jobTitle||' '||job.jobDescription||' '||comp.companyName";
        final String tokens[] = searchBody.getQuery().split(" ");
        final String ftsQuery = String.join(" | ", tokens);

        /*TODO convert to StringBuilder for building more complex queries*/
        String first = String.format("SELECT job FROM JobMatch job LEFT JOIN FETCH job.company comp LEFT JOIN FETCH comp.address WHERE fts(%s, '%s')", TABLE_COLUMNS, ftsQuery);
        String second = searchBody.getFresh()
                ? String.format("SELECT COUNT(job) FROM JobMatch job LEFT JOIN job.company comp LEFT JOIN comp.address WHERE fts(%s, '%s')", TABLE_COLUMNS, ftsQuery)
                : Constants.EMPTY_STRING;

        return Pair.of(first, second);
    }

    public String deleteById(Long id) {
        jobMatchRepository.deleteById(id);
        return "DELETED JOB_AD WITH ID " + id;
    }

}


