package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTrueque;
import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTrueque;

import java.util.List;
import java.util.Map;

public interface TruequeService {

    void saveTrueque(Map<Integer,List<Item>> participants);

    List<UserTrueque> getUserTruequeById_UserId(long id);

    List<UserTrueque> getUserTruequeById_TruequeId(long id);

    List<ItemTrueque> getItemsTruequeById_TruequeId(long id);

    Trueque getTruequeById (long id);

    Trueque findTruequeByIdAndStatusIsNotIn(Long id, int[] statuses);

    List<User> confirmTruequeAndGetUsersBelongingTo(Long id);

    void updateTrueque (Trueque trueque);

    Map getTruequeDetail(Long truequeId);

    void updateTrueque(Long idTrueque, Integer status);

    void updateTrueque(Long idTrueque, List<Long> idItems, Long idUser);

    void deleteItemTrueque(Long idTrueque, Long idUser);

    void saveItemTrueque(Long idTrueque, List<Long> idItems);
}
