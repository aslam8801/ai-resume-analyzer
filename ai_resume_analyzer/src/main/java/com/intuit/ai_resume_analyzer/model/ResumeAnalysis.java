package com.intuit.ai_resume_analyzer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "resume_analysis")
public class ResumeAnalysis {

    @Id
    private String id;

    private String userId;

    private String resumeText;

    private String jobDescription;

    private int matchScore;

    private List<String> missingSkills;

    private List<String> strengths;

    private List<String> improvements;

}