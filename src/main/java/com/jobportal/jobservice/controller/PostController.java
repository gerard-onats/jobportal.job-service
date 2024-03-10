package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.request.SearchBody;
import com.jobportal.jobservice.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("search/")
    public ResponseEntity<Map>
        search(SearchBody searchBody) {
        return ResponseEntity.ok(postService.search(searchBody));
    }
}
