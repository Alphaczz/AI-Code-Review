package com.aitool.codereview.model;

import com.aitool.codereview.ENUM.GlobalRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String githubId;

    private String githubUsername;

    @Column(length = 1000)
    private String githubAccessToken;

    @Enumerated(EnumType.STRING)
    private GlobalRole globalRole;

    private boolean enabled;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.enabled = true;
    }
}
