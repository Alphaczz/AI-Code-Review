package com.aitool.codereview.repository;

import com.aitool.codereview.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);

}
