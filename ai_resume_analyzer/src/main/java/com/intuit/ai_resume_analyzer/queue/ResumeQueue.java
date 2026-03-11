package com.intuit.ai_resume_analyzer.queue;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ResumeQueue {

    private final BlockingQueue<ResumeTask> queue = new LinkedBlockingQueue<>();

    public void submit(ResumeTask task) {
        queue.offer(task);
    }

    public ResumeTask take() throws InterruptedException {
        return queue.take();
    }

}