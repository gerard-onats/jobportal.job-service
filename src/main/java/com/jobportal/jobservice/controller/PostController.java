package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.model.Application;
import com.jobportal.jobservice.request.SearchBody;
import com.jobportal.jobservice.service.PostService;
import com.jobportal.jobservice.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin
@PreAuthorize("hasAnyRole('ROLE_USER') && isAuthenticated()")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    QuestionService questionService;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping("search/")
    public ResponseEntity<Map>
        search(SearchBody searchBody) {
        return ResponseEntity.ok(postService.search(searchBody));
    }

    @GetMapping("questions/{id}")
    public ResponseEntity<List> questions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(questionService.findQuestionById(id));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.deleteById(id));
    }

    @PostMapping(value = "form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> form(@ModelAttribute Application application) {
        MultipartFile file = application.getFile();
        String fileName = file.getName();
        String fileType = file.getContentType();
        Long fileSize = file.getSize();

        final Long KILOBYTE = 1024L;

        String result = String.format("file_name = %s, type = %s, size = %d", fileName, fileType, fileSize / KILOBYTE);
        return ResponseEntity.ok(result);
    }
}
