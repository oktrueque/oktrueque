package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.repository.TruequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by Envy on 15/6/2017.
 */
@Service
public class TruequeService {

    @Autowired
    private TruequeRepository truequeRepository;
    @Autowired
    private ItemService itemService;
    
    @Transactional
    public void saveTrueque(List<Item> itemsOffer, List<Item> itemsDemand){
        User offerer = itemsOffer.get(0).getUser();
        User demandant = itemsDemand.get(0).getUser();
        Trueque trueque = truequeRepository.save(new Trueque(offerer, demandant, 0));
        itemService.updateTruequeItems(itemsOffer,itemsDemand, trueque);
    }
}
