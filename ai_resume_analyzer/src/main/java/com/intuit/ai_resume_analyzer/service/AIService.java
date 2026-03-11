package com.intuit.ai_resume_analyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.ai_resume_analyzer.model.AIResult;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AIService {

    private final WebClient webClient;
    private final RateLimiter rateLimiter;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String key;

    public AIResult analyze(String resumeText, String jobDescription) {

        try {

            String prompt = """
                    Analyze this resume against the job description.

                    Return ONLY valid JSON.

                    Format:

                    {
                      "matchScore": number,
                      "missingSkills": [],
                      "strengths": [],
                      "improvements": []
                    }

                    Resume:
                    """ + resumeText + """

                    Job Description:
                    """ + jobDescription;


            Map<String, Object> body =
                    Map.of(
                            "contents",
                            List.of(
                                    Map.of(
                                            "parts",
                                            List.of(
                                                    Map.of("text", prompt)
                                            )
                                    )
                            )
                    );


            Supplier<String> supplier =
                    RateLimiter.decorateSupplier(rateLimiter, () ->
                            webClient.post()
                                    .uri("/v1beta/models/gemini-flash-latest:generateContent?key=" + key)
                                    .bodyValue(body)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block()
                    );


            String aiResponse = supplier.get();


            JsonNode root = objectMapper.readTree(aiResponse);


            String text = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();


            // remove markdown if Gemini returns ```json
            text = text.replace("```json", "")
                    .replace("```", "")
                    .trim();


            return objectMapper.readValue(text, AIResult.class);

        } catch (Exception e) {
            throw new RuntimeException("AI analysis failed", e);
        }
    }
}