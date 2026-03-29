package com.aitool.codereview.model;


import com.aitool.codereview.ENUM.PRStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubPR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer prNumber;

    private String prTitle;

    private String prUrl;

    private String authorGithubUsername;

    @Enumerated(EnumType.STRING)
    private PRStatus status;

    private String baseBranch;

    private String headBranch;

    private String diffUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    private ConnectedRepo connectedRepo;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = PRStatus.OPEN;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
