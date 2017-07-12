package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.repository.TruequeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by Envy on 15/6/2017.
 */
public class TruequeServiceImpl implements TruequeService {


    private final TruequeRepository truequeRepository;
    @Autowired
    private ItemService itemServiceImpl;

    public TruequeServiceImpl(TruequeRepository truequeRepository){
        this.truequeRepository = truequeRepository;
    }
    
    @Transactional
    public void saveTrueque(List<Item> itemsOffer, List<Item> itemsDemand){
        User offerer = itemsOffer.get(0).getUser();
        User demandant = itemsDemand.get(0).getUser();
        Trueque trueque = truequeRepository.save(new Trueque(offerer, demandant, 0));
        itemServiceImpl.updateTruequeItems(itemsOffer,itemsDemand, trueque);
    }
}
