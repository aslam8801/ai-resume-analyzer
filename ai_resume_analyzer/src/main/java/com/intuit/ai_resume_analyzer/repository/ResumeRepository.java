package com.intuit.ai_resume_analyzer.repository;

import com.intuit.ai_resume_analyzer.model.ResumeAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResumeRepository extends MongoRepository<ResumeAnalysis, String> {

    List<ResumeAnalysis> findByUserId(String userId);

}