package com.oktrueque.repository;

import com.oktrueque.model.UserTrueque;
import com.oktrueque.model.UserTruequeId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Facundo on 8/23/2017.
 */
public interface UserTruequeRepository extends PagingAndSortingRepository<UserTrueque,UserTruequeId> {

    List<UserTrueque> getUserTruequeById_UserId(long id);

    List<UserTrueque> getUserTruequeById_TruequeId(long id);


}
