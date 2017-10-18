package com.oktrueque.service;

import com.oktrueque.model.Comment;
import com.oktrueque.model.User;
import com.oktrueque.repository.CommentRepository;
import com.oktrueque.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */


public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        comment = commentRepository.save(comment);
        updateUserScore(comment);
        return comment;
    }

    @Override
    public void updateUserScore(Comment comment){
        List<Comment> comentarios = commentRepository.findByUserTarget_Id(comment.getUserTarget().getId());
        Integer suma = 0, cont = 0;
        for (Comment comentario: comentarios){
            suma += comentario.getScore();
            cont++;
        }
        User user = userRepository.findByEmailOrUsername("",comment.getUserTarget().getUsername());
        Integer prom = suma/cont;
        user.setScore(prom); //Esto da nullPointerException
        userRepository.save(user);
    }

    @Override
    public List<Comment> getCommentsByUserTargetId(Long userTargetId){
        return commentRepository.findByUserTarget_Id(userTargetId);
    }


}
