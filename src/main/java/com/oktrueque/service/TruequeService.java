package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;

import java.util.List;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface TruequeService {

    void saveTrueque(List<Item> itemsOffer, List<Item> itemsDemand);

    List<Trueque> findByUserOffererIdOrUserDemandantId(Long userOffererId, Long userDemandantId);
}
