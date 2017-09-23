package com.oktrueque.repository;

import com.oktrueque.model.Trueque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TruequeRepository extends CrudRepository<Trueque, Long> {

    Trueque findTruequeById(long id);
    Trueque findTruequeByIdAndStatusIsNotIn(Long id, int[] statuses);
}
