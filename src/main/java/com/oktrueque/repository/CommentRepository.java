package com.oktrueque.repository;

import com.oktrueque.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> findByUserTarget_Id(Long user_id);

    Long countCommentsByUserTarget_Id(Long userId);

    @Query(value="SELECT sum(c.score) FROM comments c WHERE c.id_user_target = :userTargetId GROUP BY c.id_user_target", nativeQuery = true)
    Long sumScoreByUserTargetId(@Param("userTargetId") Long userTargetId);


}
