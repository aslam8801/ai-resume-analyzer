package com.intuit.ai_resume_analyzer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ResumeParserService {

    public String parse(MultipartFile file) {

        try {

            InputStream inputStream = file.getInputStream();

            PDDocument document = PDDocument.load(inputStream);

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            document.close();

            return text;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse resume", e);
        }

    }
}