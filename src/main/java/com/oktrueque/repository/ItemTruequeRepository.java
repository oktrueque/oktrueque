package com.oktrueque.repository;

import com.oktrueque.model.ItemTrueque;
import com.oktrueque.model.ItemTruequeId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Facundo on 8/23/2017.
 */
public interface ItemTruequeRepository extends PagingAndSortingRepository<ItemTrueque,ItemTruequeId> {

    List<ItemTrueque> findById_TruequeId(long id);
}
