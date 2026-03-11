package com.intuit.ai_resume_analyzer.model;

import lombok.Data;

import java.util.List;

@Data
public class AIResult {

    private int matchScore;

    private List<String> missingSkills;

    private List<String> strengths;

    private List<String> improvements;

}