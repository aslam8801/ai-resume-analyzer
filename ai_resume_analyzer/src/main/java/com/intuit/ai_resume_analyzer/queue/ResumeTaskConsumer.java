package com.intuit.ai_resume_analyzer.queue;

import com.intuit.ai_resume_analyzer.model.AIResult;
import com.intuit.ai_resume_analyzer.model.ResumeAnalysis;
import com.intuit.ai_resume_analyzer.repository.ResumeRepository;
import com.intuit.ai_resume_analyzer.service.AIService;
import com.intuit.ai_resume_analyzer.service.ResumeParserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeTaskConsumer {

    private final ResumeQueue queue;
    private final ResumeParserService parserService;
    private final AIService aiService;
    private final ResumeRepository repository;

    @PostConstruct
    public void startWorker() {

        new Thread(() -> {

            while (true) {

                try {

                    ResumeTask task = queue.take();

                    String resumeText =
                            parserService.parse(task.getResumeFile());

                    AIResult result =
                            aiService.analyze(resumeText, task.getJobDescription());

                    ResumeAnalysis analysis = new ResumeAnalysis();

                    analysis.setUserId(task.getUserId());

                    analysis.setResumeText(resumeText);
                    analysis.setJobDescription(task.getJobDescription());

                    analysis.setMatchScore(result.getMatchScore());
                    analysis.setMissingSkills(result.getMissingSkills());
                    analysis.setStrengths(result.getStrengths());
                    analysis.setImprovements(result.getImprovements());

                    repository.save(analysis);

                    System.out.println("Resume analysis saved");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
}