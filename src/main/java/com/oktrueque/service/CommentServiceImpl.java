package com.oktrueque.service;

import com.oktrueque.model.Comment;
import com.oktrueque.repository.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */


public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        return comments;
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        Comment CommentSaved = commentRepository.save(comment);
        return CommentSaved;
    }

    @Override
    public List<Comment> getCommentsByUserTargetId(Long userTargetId){
        return commentRepository.findByUserTarget_Id(userTargetId);
    }

}
