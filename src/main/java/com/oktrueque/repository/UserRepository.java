package com.oktrueque.repository;

import com.oktrueque.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

     User findByEmailOrUsername(String email, String username);

     @Query("SELECT COUNT(e) FROM User e WHERE e.email=?1 OR e.username=?2")
     Long checkIfUserExists(String email, String username);

     User findByUsername(String username);



}
