package com.oktrueque.repository;

import com.oktrueque.model.ItemTrueque;
import com.oktrueque.model.ItemTruequeId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemTruequeRepository extends PagingAndSortingRepository<ItemTrueque,ItemTruequeId> {

    List<ItemTrueque> findById_TruequeId(long id);
    List<ItemTrueque> findById_ItemId(long id);
    void deleteAllByIdTruequeIdAndIdItemUserId(Long truequeId, Long userId);
    List<ItemTrueque> findAllById_ItemIdAndId_TruequeIdIsNotAndId_TruequeStatus(Long itemId, Long truequeId, Integer status);
}
