package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;

import java.util.List;
import java.util.Map;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface TruequeService {

    void saveTrueque(Map<Integer,List<Item>> participants);

}
