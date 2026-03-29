package com.aitool.codereview.model;
import com.aitool.codereview.ENUM.ComplexityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int bugScore;

    @Enumerated(EnumType.STRING)
    private ComplexityLevel complexityLevel;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    @Column(columnDefinition = "TEXT")
    private String securityIssues;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private LocalDateTime reviewedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_id")
    private GitHubPR githubPR;

    @PrePersist
    public void prePersist() {
        this.reviewedAt = LocalDateTime.now();
    }
}
