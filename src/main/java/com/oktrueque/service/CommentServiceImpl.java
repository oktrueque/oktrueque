package com.oktrueque.service;

import com.oktrueque.model.Comment;
import com.oktrueque.repository.CommentRepository;

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

    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        return comments;
    }
}