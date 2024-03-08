package com.jobportal.jobservice.service;

import com.jobportal.jobservice.DTO.JobMatchDTO;
import com.jobportal.jobservice.constants.Constants;
import com.jobportal.jobservice.model.Address;
import com.jobportal.jobservice.model.JobMatch;
import com.jobportal.jobservice.repository.JobMatchRepository;
import com.jobportal.jobservice.utils.FormatterUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.*;

@Service
public class PostService {
    @Autowired
    AmazonS3Service s3Service;

    @PersistenceContext
    EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Secured("ROLE_USER")
    public Map search(String query, String location, String jobType, String datePosted, String preference, String experienceLevel, boolean isNewSearch, Long pageNumber) {
        StringBuilder sb = new StringBuilder();
        String words[] = query.split(" ");

        boolean isFirst = true;
        for(int i = 0; i < words.length; i++) {
            if(!isFirst) sb.append(" | ");
            sb.append(words[i]);
            isFirst = false;
        }

        final String fixedQuery = sb.toString();
        final String JOB_COLUMNS = "job.jobTitle||' '||job.jobDescription||' '||comp.companyName";
        String queryString = String.format("SELECT job FROM JobMatch job LEFT JOIN FETCH job.company comp LEFT JOIN FETCH comp.address WHERE fts(%s, '%s')", JOB_COLUMNS, fixedQuery);

        logger.info("Search for QUERY = {}", fixedQuery);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Query q = em.createQuery(queryString);
        final int firstResultIndex = Long.valueOf(pageNumber - 1).intValue() * Constants.DEFAULT_RESULTS_PER_PAGE;

        List<JobMatch> matches = q
                .setFirstResult(firstResultIndex)
                .setMaxResults(Constants.DEFAULT_RESULTS_PER_PAGE)
                .getResultList();

        stopWatch.stop();
        logger.info("(QUERY {}) completed in {}ms", fixedQuery, stopWatch.getLastTaskTimeMillis());

        /* TODO Retrieve image objects in S3, and process using CompletableFuture for large batches */
        Map<String, String> exists = new HashMap<>();

        stopWatch.start();
        List<JobMatchDTO> result = new ArrayList<>();
        for(JobMatch match : matches) {
            JobMatchDTO matchDTO = new JobMatchDTO();

            String companyName = match.getCompany().getCompanyName();
            matchDTO.setCompany(companyName);
            matchDTO.setDescription(match.getJobDescription());
            matchDTO.setTitle(match.getJobTitle());
            matchDTO.setCompanyDescription(match.getCompany().getCompanyDescription());

            String logo = match.getCompany().getLogo();
            if(exists.containsKey(logo)) {
                long st = System.currentTimeMillis();
                matchDTO.setBase64Image(exists.get(logo));
                long take = System.currentTimeMillis() - st;
                logger.info("Already hashed, took {}ms", take);
            }
            else {
                String base64 = s3Service.getObjectBase64(logo);
                String base64Image = FormatterUtils.base64LogoFormatter(logo, base64);
                matchDTO.setBase64Image(base64Image);
                exists.put(logo, base64Image);
            }

            Date dateAdded = match.getDateAdded();
            matchDTO.setTimePosted(FormatterUtils.datePostedFormatter(dateAdded));

            Address matchAddress = match.getCompany().getAddress();
            matchDTO.setLocation(FormatterUtils.locationFormatter(matchAddress));

            result.add(matchDTO);
        }

        stopWatch.stop();
        logger.info("Building DTO for query results took {}ms", stopWatch.getLastTaskTimeMillis());

        Map<String, Object> jsonResult = new HashMap<>();
        jsonResult.put("results", result);

        if(isNewSearch) {
            final String queryStringResultCount = String.format("SELECT COUNT(job) FROM JobMatch job LEFT JOIN job.company comp LEFT JOIN comp.address WHERE fts(%s, '%s')", JOB_COLUMNS, fixedQuery);
            q = em.createQuery(queryStringResultCount);

            Long resultsMatch = (Long) q.getSingleResult();
            Long pagesNeeded = (resultsMatch + Constants.DEFAULT_RESULTS_PER_PAGE - 1) / Constants.DEFAULT_RESULTS_PER_PAGE;

            jsonResult.put("resultMatch", resultsMatch);
            jsonResult.put("pagesNeeded", pagesNeeded);
        }

        return jsonResult;
    }
}
