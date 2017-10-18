package com.oktrueque.service;

import com.oktrueque.model.Comment;
import com.oktrueque.model.User;
import com.oktrueque.repository.CommentRepository;
import org.springframework.data.domain.Page;
import com.oktrueque.repository.UserRepository;
import org.springframework.data.domain.Pageable;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

        User user = userRepository.findUserById(comment.getUserTarget().getId());
        Integer prom = suma/cont;
        user.setScore(prom); //Esto da nullPointerException
        userRepository.save(user);
    }

    @Override
    public List<Comment> getCommentsByUserTargetId(Long userTargetId){
        return commentRepository.findByUserTarget_Id(userTargetId);
    }

    @Override
    public Page<Comment> getCommentsByUserTargetId(Long userTargetId, Pageable pageable) {
        return commentRepository.findByUserTarget_Id(userTargetId, pageable);
    }

}
