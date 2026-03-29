package com.aitool.codereview.controller;

import com.aitool.codereview.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.oauth.url}")
    private String tokenUrl;

    @Value("${github.api.user.url}")
    private String userUrl;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/github")
    public ResponseEntity<String> redirectToGitHub() {

        String url = "https://github.com/login/oauth/authorize" +
                "?client_id=" + clientId +
                "&scope=repo,user" +
                "&redirect_uri=" + redirectUri;

        return ResponseEntity.ok(url);
    }

    @GetMapping("/github/callback")
    public ResponseEntity<String> handleCallback(@RequestParam String code) {

        String jwt = authService.handleGitHubCallback(
                code,
                clientId,
                clientSecret,
                tokenUrl,
                userUrl,
                redirectUri
        );

        return ResponseEntity.ok(jwt);
    }
}
