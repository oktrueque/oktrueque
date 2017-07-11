package com.oktrueque.repository;

import com.oktrueque.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Facundo on 11/07/2017.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Long>{

    VerificationToken findByToken(String token);
}
