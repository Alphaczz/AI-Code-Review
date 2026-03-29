package com.aitool.codereview.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer lineNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long githubCommentId;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private GitHubPR githubPR;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
