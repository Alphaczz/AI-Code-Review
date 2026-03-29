package com.aitool.codereview.service;


import com.aitool.codereview.exception.GitHubApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubApiService {

    private final RestTemplate restTemplate;

    public List<Map<String, Object>> getUserRepos(String accessToken) {
        String url = "https://api.github.com/user/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to fetch repos", e);
            throw new GitHubApiException("Unable to fetch repositories");
        }
    }

    public Map<String, Object> createWebhook(String accessToken,
                                             String repoFullName,
                                             String webhookUrl,
                                             String secret) {

        String url = "https://api.github.com/repos/" + repoFullName + "/hooks";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> config = Map.of(
                "url", webhookUrl,
                "content_type", "json",
                "secret", secret
        );

        Map<String, Object> body = Map.of(
                "name", "web",
                "active", true,
                "events", List.of("pull_request"),
                "config", config
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Webhook creation failed", e);
            throw new GitHubApiException("Failed to create webhook");
        }
    }

    public String getPRDiff(String accessToken, String diffUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.valueOf("application/vnd.github.v3.diff")));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(diffUrl, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch PR diff");
        }
    }
}
