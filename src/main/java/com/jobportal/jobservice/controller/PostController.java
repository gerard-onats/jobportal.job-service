package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.request.SearchBody;
import com.jobportal.jobservice.service.PostService;
import com.jobportal.jobservice.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    QuestionService questionService;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PreAuthorize("hasAnyRole('ROLE_USER') && isAuthenticated()")
    @GetMapping("search/")
    public ResponseEntity<Map>
        search(SearchBody searchBody) {
        return ResponseEntity.ok(postService.search(searchBody));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER') && isAuthenticated()")
    @GetMapping("questions/{id}")
    public ResponseEntity<List> questions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(questionService.findQuestionById(id));
    }
}
