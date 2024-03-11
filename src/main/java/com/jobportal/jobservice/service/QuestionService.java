package com.jobportal.jobservice.service;

import com.jobportal.jobservice.model.Question;
import com.jobportal.jobservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }
}
