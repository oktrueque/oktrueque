package com.oktrueque.repository;

import com.oktrueque.model.UserLite;
import org.springframework.data.repository.CrudRepository;

public interface UserLiteRepository extends CrudRepository<UserLite, Long> {

    UserLite findUserById(long id);

    UserLite findByEmailOrUsername(String email, String username);

    UserLite findByUsername(String username);

}
