package com.aitool.codereview.service;


import com.aitool.codereview.ENUM.GlobalRole;
import com.aitool.codereview.model.User;
import com.aitool.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final RestTemplate restTemplate = new RestTemplate();

    public String handleGitHubCallback(String code,
                                       String clientId,
                                       String clientSecret,
                                       String tokenUrl,
                                       String userUrl,
                                       String redirectUri) {

        // Step 1: Exchange code for access token
        String accessToken = getAccessToken(code, clientId, clientSecret, tokenUrl, redirectUri);

        // Step 2: Fetch GitHub user
        Map<String, Object> userData = getGitHubUser(accessToken, userUrl);

        String githubId = String.valueOf(userData.get("id"));
        String username = (String) userData.get("login");
        String email = (String) userData.get("email");
        String name = (String) userData.get("name");

        // Step 3: Save or update user
        User user = userRepository.findByGithubId(githubId)
                .map(existing -> updateUser(existing, accessToken))
                .orElseGet(() -> createUser(githubId, username, email, name, accessToken));

        // Step 4: Generate JWT
        return jwtService.generateToken(user.getGithubUsername());
    }

    private String getAccessToken(String code, String clientId, String clientSecret,
                                  String tokenUrl, String redirectUri) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code,
                "redirect_uri", redirectUri
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    private Map<String, Object> getGitHubUser(String accessToken, String userUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(userUrl, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    private User createUser(String githubId, String username,
                            String email, String name, String token) {

        User user = User.builder()
                .githubId(githubId)
                .githubUsername(username)
                .email(email)
                .fullName(name)
                .githubAccessToken(token)
                .globalRole(GlobalRole.USER)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    private User updateUser(User user, String token) {
        user.setGithubAccessToken(token);
        return userRepository.save(user);
    }
}
