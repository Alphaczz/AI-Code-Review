package com.aitool.codereview.repository;

import com.aitool.codereview.model.AIReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIReviewRepository    extends JpaRepository<AIReview, Long> {
    Optional<AIReview> findByGithubPRId(Long prId);

}
