package com.oktrueque.repository;

import com.oktrueque.model.UserTrueque;
import com.oktrueque.model.UserTruequeId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Facundo on 8/23/2017.
 */
public interface UserTruequeRepository extends PagingAndSortingRepository<UserTrueque,UserTruequeId>{
}
