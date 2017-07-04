package com.oktrueque.repository;

import com.oktrueque.model.Comment;
import org.springframework.data.repository.CrudRepository;



/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
