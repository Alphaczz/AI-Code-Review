package com.aitool.codereview.service;


import com.aitool.codereview.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class WebhookService {

    @Value("${webhook.secret}")
    private String secret;

    public void processWebhook(String event, String signature, String payload) {

        if (!isValidSignature(payload, signature)) {
            throw new BadRequestException("Invalid webhook signature");
        }

        if ("pull_request".equals(event)) {
            handlePullRequestEvent(payload);
        }
    }

    private boolean isValidSignature(String payload, String signature) {
        try {
            String expected = "sha256=" + hmacSha256(payload, secret);
            return expected.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    private String hmacSha256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
        byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();
        for (byte b : raw) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    private void handlePullRequestEvent(String payload) {
        // Next phase: parse JSON, save PR, trigger AI
    }
}

