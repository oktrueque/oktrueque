package com.oktrueque.repository;

import com.oktrueque.model.UserTrueque;
import com.oktrueque.model.UserTruequeId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTruequeRepository extends PagingAndSortingRepository<UserTrueque,UserTruequeId> {

    List<UserTrueque> findByIdUserId(Long id);

    List<UserTrueque> findByIdTruequeId(Long id);

    @Query(value="SELECT * FROM trueques t INNER JOIN users_trueques ut " +
            "ON t.id=ut.id_trueque " +
            "WHERE ut.id_trueque IN :idTrueques" +
            " ORDER BY t.proposal_date DESC", nativeQuery = true)
     List<UserTrueque> findTruequesInOrder(@Param("idTrueques") List<Long> idTrueques);

}
