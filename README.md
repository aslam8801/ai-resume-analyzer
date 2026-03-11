# AI Resume Analyzer 🚀

An AI-powered backend system that analyzes resumes against job descriptions using **Spring Boot, MongoDB Atlas, and Google Gemini AI**.

## Features

- Upload Resume (PDF)
- AI-powered skill analysis
- Match score calculation
- Missing skills detection
- Strength & improvement suggestions
- Async processing using worker queue
- MongoDB Atlas cloud storage
- Resume history per user

## Tech Stack

Backend:
- Java 21
- Spring Boot
- MongoDB Atlas
- Google Gemini AI API

Architecture:
- Async worker queue
- REST API
- Cloud database

## API Endpoints

### Analyze Resume

POST : /api/resume/analyze

Form Data:
resume : file
jobDescription : text
userId : string


### Get User History

GET
/api/resume/history/{userId}


### Get Analysis Result

GET
/api/resume/result/{id}


## System Architecture

Frontend → Spring Boot API → Worker Queue → AI Service → MongoDB Atlas

## Setup

Clone repository
git clone https://github.com/aslam8801/ai-resume-analyzer.git


Run backend
mvn spring-boot:run


Environment variables:
GEMINI_API_KEY=your_key
MONGO_URI=your_mongodb_uri

## Future Improvements

- React frontend
- Authentication
- Docker deployment
- Resume ranking system
