package com.oktrueque.repository;

import com.oktrueque.model.ComplaintType;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;
import java.util.List;

public interface ComplaintTypeRepository extends CrudRepository<ComplaintType, Integer> {

    ComplaintType getById(long id);
    List<ComplaintType> findAllByIdLessThan(Long idComplaintType);

}
