package com.oktrueque.repository;

import com.oktrueque.model.ItemTrueque;
import com.oktrueque.model.ItemTruequeId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemTruequeRepository extends PagingAndSortingRepository<ItemTrueque,ItemTruequeId> {

    List<ItemTrueque> findById_TruequeId(long id);
}
