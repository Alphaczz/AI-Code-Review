package com.aitool.codereview.repository;

import com.aitool.codereview.model.ConnectedRepo;
import com.aitool.codereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectedRepoRepository extends JpaRepository<ConnectedRepo, Long> {
    List<ConnectedRepo> findByUserId(Long userId);
    Optional<ConnectedRepo> findByRepoId(Long repoId);

}
