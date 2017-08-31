package com.oktrueque.repository;

import com.oktrueque.model.Complaint;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */
public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {

    }
