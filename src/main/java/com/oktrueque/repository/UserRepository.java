package com.oktrueque.repository;

import com.oktrueque.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Felipe on 7/5/2017.
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByEmail(String email);
}
