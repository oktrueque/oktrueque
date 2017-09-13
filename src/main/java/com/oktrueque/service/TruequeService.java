package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTrueque;
import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTrueque;

import java.util.List;
import java.util.Map;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface TruequeService {

    void saveTrueque(Map<Integer,List<Item>> participants);

    List<UserTrueque> getUserTruequeById_UserId(long id);

    List<UserTrueque> getUserTruequeById_TruequeId(long id);

    List<ItemTrueque> getItemsTruequeById_TruequeId(long id);

    Trueque getTruequeById (long id);

    List<User> confirmTruequeAndGetUsersBelongingTo(Long id);

    void updateTrueque (Trueque trueque);




}
