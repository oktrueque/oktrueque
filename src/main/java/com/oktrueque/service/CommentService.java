package com.oktrueque.service;

import com.oktrueque.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> getComments();

    Comment saveComment(Comment comment);

    List<Comment> getCommentsByUserTargetId(Long userTargetId);

    Page<Comment> getCommentsByUserTargetId(Long userTargetId, Pageable pageable);

}
