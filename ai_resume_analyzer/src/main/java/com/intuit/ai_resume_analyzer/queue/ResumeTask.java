package com.intuit.ai_resume_analyzer.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ResumeTask {

    private MultipartFile resumeFile;

    private String jobDescription;

    private String userId;

}