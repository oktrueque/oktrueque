package com.oktrueque.service;

import com.oktrueque.model.Item;

import java.util.List;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface TruequeService {

    void saveTrueque(List<Item> itemsOffer, List<Item> itemsDemand);
}
