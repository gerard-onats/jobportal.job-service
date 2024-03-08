package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin
public class PostController {
    @Autowired
    PostService postService;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PreAuthorize("hasAnyRole('ROLE_USER') && isAuthenticated()")
    @GetMapping("/search/")
    public ResponseEntity<Map>
        search(@RequestParam(value="query") String query,
               @RequestParam(value="location", required = false) String location,
               @RequestParam(value="jobType", required = false) String jobType,
               @RequestParam(value="datePosted", required = false) String datePosted,
               @RequestParam(value="preference", required = false) String preference,
               @RequestParam(value="experienceLevel", required = false) String experienceLevel,
               @RequestParam(value="isNewSearch") boolean isNewSearch,
               @RequestParam(value="pageNumber") Long pageNumber) {
        Map result = postService.search(query, location, jobType, datePosted, preference, experienceLevel, isNewSearch, pageNumber);
        return ResponseEntity.ok(result);
    }
}
