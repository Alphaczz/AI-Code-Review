package com.aitool.codereview.controller;


import com.aitool.codereview.model.ConnectedRepo;
import com.aitool.codereview.model.User;
import com.aitool.codereview.repository.ConnectedRepoRepository;
import com.aitool.codereview.repository.UserRepository;
import com.aitool.codereview.service.GitHubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/github/repos")
@RequiredArgsConstructor
public class GitHubRepoController {

    private final GitHubApiService gitHubApiService;
    private final UserRepository userRepository;
    private final ConnectedRepoRepository connectedRepoRepository;

    @Value("${webhook.url}")
    private String webhookUrl;

    @Value("${webhook.secret}")
    private String webhookSecret;

    @GetMapping
    public ResponseEntity<?> getRepos(Principal principal) {

        User user = userRepository.findByGithubUsername(principal.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                gitHubApiService.getUserRepos(user.getGithubAccessToken())
        );
    }

    @PostMapping("/connect")
    public ResponseEntity<?> connectRepo(@RequestBody Map<String, Object> request,
                                         Principal principal) {

        String repoFullName = (String) request.get("repoFullName");
        Long repoId = Long.valueOf(request.get("repoId").toString());

        User user = userRepository.findByGithubUsername(principal.getName())
                .orElseThrow();

        Map<String, Object> webhook =
                gitHubApiService.createWebhook(
                        user.getGithubAccessToken(),
                        repoFullName,
                        webhookUrl,
                        webhookSecret
                );

        Long webhookId = Long.valueOf(webhook.get("id").toString());

        ConnectedRepo repo = ConnectedRepo.builder()
                .repoFullName(repoFullName)
                .repoId(repoId)
                .webhookId(webhookId)
                .user(user)
                .build();

        connectedRepoRepository.save(repo);

        return ResponseEntity.ok("Repository connected successfully");
    }
}
