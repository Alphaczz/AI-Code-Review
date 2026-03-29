package com.aitool.codereview.repository;

import com.aitool.codereview.model.GitHubPR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface GitHubPRRepository extends PagingAndSortingRepository<GitHubPR, Long> {
    Page<GitHubPR> findByConnectedRepoId(Long repoId, Pageable pageable);
    Optional<GitHubPR> findByPrNumberAndConnectedRepoId(Integer prNumber, Long repoId);

}
