package com.oktrueque.service;

import com.oktrueque.model.Comment;

import java.util.List;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface CommentService {

    List<Comment> getComments();

    Comment saveComment(Comment comment);

}
