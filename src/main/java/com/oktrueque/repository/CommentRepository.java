package com.oktrueque.repository;

import com.oktrueque.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> findByUserTarget_Id(Long user_id);
    Page<Comment> findByUserTarget_Id(Long user_id, Pageable pageable);

}
