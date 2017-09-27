package com.oktrueque.repository;

import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{


     User findUserById(long id);

     User findByEmailOrUsername(String email, String username);

     @Query("SELECT COUNT(e) FROM User e WHERE e.email=?1 OR e.username=?2")
     Long checkIfUserExists(String email, String username);

     User findByUsername(String username);

     //User findByStatusAndEmailOrStatusAndUsername(Integer status1, String email, Integer status2, String username);

     @Query("SELECT u FROM User u WHERE u.status=?1 AND (u.email=?2 OR u.username=?3)")
     User findUser(Integer status, String email, String username);

}
