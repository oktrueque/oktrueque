package com.oktrueque.service;

import com.oktrueque.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface TruequeService {

    Trueque saveTrueque(Map<Integer,List<Item>> participants, String username);

    List<UserTrueque> getUserTruequeById_UserId(long id);

    List<UserTrueque> getUserTruequeById_TruequeId(long id);

    List<UserTrueque> getUserTruequeById_TruequeId_OrderByOrder(long id);

    List<ItemTrueque> getItemsTruequeById_TruequeId(long id);

    Trueque getTruequeById (long id);

    void acceptTruequeAndGetUsersBelongingTo(Long id, String username);

    void updateTrueque (Trueque trueque, User user);


    Map getTruequeDetail(Long truequeId);

    void updateTrueque(Long idTrueque, Integer status);

    String updateTrueque(Long idTrueque, List<Long> idItems, Long idUser);

    void deleteItemTrueque(Long idTrueque, Long idUser);

    void saveItemTrueque(Long idTrueque, List<Long> idItems);

    boolean isTimeToAsk(Trueque trueque);

    Map<String, Object> confirmTrueque(Long id, String username);

    void updateItemStatus(List<Item> itemsInTrueque, int itemStatusExchanged);

    List<UserTrueque> getUserTruequesInOrder(List<Long> truequesId);
}
