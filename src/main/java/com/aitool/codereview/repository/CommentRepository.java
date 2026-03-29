package com.aitool.codereview.repository;

import com.aitool.codereview.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGithubPRId(Long prId);

}
