package com.intuit.ai_resume_analyzer.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(30))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(30))
                                .addHandlerLast(new WriteTimeoutHandler(30)));

        return WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}