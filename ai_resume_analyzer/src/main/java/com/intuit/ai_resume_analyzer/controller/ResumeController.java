package com.intuit.ai_resume_analyzer.controller;

import com.intuit.ai_resume_analyzer.model.ResumeAnalysis;
import com.intuit.ai_resume_analyzer.queue.ResumeQueue;
import com.intuit.ai_resume_analyzer.queue.ResumeTask;
import com.intuit.ai_resume_analyzer.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeQueue queue;
    private final ResumeRepository repository;

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("userId") String userId) {

        ResumeTask task = new ResumeTask(resume, jobDescription, userId);

        queue.submit(task);

        return "Resume submitted for background analysis";
    }

    @GetMapping("/history/{userId}")
    public List<ResumeAnalysis> history(@PathVariable String userId) {

        return repository.findByUserId(userId);

    }

    @GetMapping("/result/{id}")
    public ResumeAnalysis getResult(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

    }
}