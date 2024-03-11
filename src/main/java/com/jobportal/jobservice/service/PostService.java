package com.jobportal.jobservice.service;

import com.jobportal.jobservice.DTO.JobMatchDTO;
import com.jobportal.jobservice.constants.Constants;
import com.jobportal.jobservice.model.Address;
import com.jobportal.jobservice.model.JobMatch;
import com.jobportal.jobservice.request.SearchBody;
import com.jobportal.jobservice.utils.FormatterUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class PostService {
    @Autowired
    AmazonS3Service s3Service;

    @PersistenceContext
    EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public Map search(SearchBody searchBody) {
        final Pair<String, String> queries = buildQueries(searchBody);
        final int firstResultIndex = Long.valueOf(searchBody.getPageNumber() - 1).intValue() * Constants.DEFAULT_RESULTS_PER_PAGE;

        List query = em
                .createQuery(queries.getFirst())
                .setFirstResult(firstResultIndex)
                .setMaxResults(Constants.DEFAULT_RESULTS_PER_PAGE)
                .getResultList();

        Map<String, Object> result = new HashMap<>();
        result.put("results", buildFormattedDTO(query));

        if(searchBody.isNewSearch()) {
            Assert.isTrue(queries.getSecond().equals(Constants.EMPTY_STRING), "Cannot be!");
            setExtraMetaData(result, queries.getSecond());
        }

        return result;
    }

    private List<JobMatchDTO> buildFormattedDTO(List<JobMatch> query) {
        List<JobMatchDTO> result = new ArrayList<>();
        Map<String, String> exists = new HashMap<>(); //extra cache to prevent costly encoding

        /* TODO Retrieve image objects in S3, and process using CompletableFuture for larger batches */
        for(JobMatch match : query) {
            JobMatchDTO matchDTO = new JobMatchDTO();

            matchDTO.setCompany(match.getCompany().getCompanyName());
            matchDTO.setDescription(match.getJobDescription());
            matchDTO.setTitle(match.getJobTitle());
            matchDTO.setCompanyDescription(match.getCompany().getCompanyDescription());

            String logo = match.getCompany().getLogo();
            if(exists.containsKey(logo)) matchDTO.setBase64Image(exists.get(logo));
            else {
                String base64Image = FormatterUtils.base64LogoFormatter(logo, s3Service.getObjectBase64(logo));
                matchDTO.setBase64Image(base64Image);
                exists.put(logo, base64Image);
            }

            Date dateAdded = match.getDateAdded();
            matchDTO.setTimePosted(FormatterUtils.datePostedFormatter(dateAdded));

            Address matchAddress = match.getCompany().getAddress();
            matchDTO.setLocation(FormatterUtils.locationFormatter(matchAddress));

            result.add(matchDTO);
        }

        return result;
    }

    private Pair<String, String> buildQueries(SearchBody searchBody) {
        final String TABLE_COLUMNS = "job.jobTitle||' '||job.jobDescription||' '||comp.companyName";
        final String ftsQuery = ftsStringQuery(searchBody.getQuery());

        /*TODO convert to StringBuilder for building more complex queries*/
        String first = String.format("SELECT job FROM JobMatch job LEFT JOIN FETCH job.company comp LEFT JOIN FETCH comp.address WHERE fts(%s, '%s')", TABLE_COLUMNS, ftsQuery);
        String second = searchBody.isNewSearch()
                ? String.format("SELECT COUNT(job) FROM JobMatch job LEFT JOIN job.company comp LEFT JOIN comp.address WHERE fts(%s, '%s')", TABLE_COLUMNS, ftsQuery)
                : Constants.EMPTY_STRING;

        return Pair.of(first, second);
    }

    private String ftsStringQuery(String query) {
        StringBuilder sb = new StringBuilder();
        String[] tokens = query.split(" ");

        boolean isFirst = true;
        for(String token : tokens) {
            if(!isFirst) sb.append(" | ");
            sb.append(token);
            isFirst = false;
        }

        return sb.toString();
    }

    private void setExtraMetaData(Map map, String queryString) {
        Query q = em.createQuery(queryString);

        Long resultsMatch = (Long) q.getSingleResult();
        Long pagesNeeded = (resultsMatch + Constants.DEFAULT_RESULTS_PER_PAGE - 1) / Constants.DEFAULT_RESULTS_PER_PAGE;

        map.put("resultMatch", resultsMatch);
        map.put("pagesNeeded", pagesNeeded);
    }
}


