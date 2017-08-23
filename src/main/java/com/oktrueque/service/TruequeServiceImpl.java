package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TruequeRepository;
import com.oktrueque.repository.UserTruequeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * Created by Envy on 15/6/2017.
 */

public class TruequeServiceImpl implements TruequeService {


    private final TruequeRepository truequeRepository;

    private final ItemTruequeRepository itemTruequeRepository;

    private final UserTruequeRepository userTruequeRepository;

    public TruequeServiceImpl(TruequeRepository truequeRepository,
                              ItemTruequeRepository itemTruequeRepository,
                              UserTruequeRepository userTruequeRepository){
        this.truequeRepository = truequeRepository;
        this.itemTruequeRepository = itemTruequeRepository;
        this.userTruequeRepository = userTruequeRepository;
    }
    
    @Transactional
    public void saveTrueque(Map<Integer,List<Item>> participants){
        Trueque truequeToSave = new Trueque(0);
        truequeToSave.setPeopleCount(participants.size());
        Trueque truequeSaved = truequeRepository.save(truequeToSave);
        saveItemsAndUsers(participants, truequeSaved);
    }

    private void saveItemsAndUsers(Map<Integer, List<Item>> participants, Trueque truequeSaved) {
        participants.entrySet().forEach(entry -> {
            userTruequeRepository.save(
                    createUserTrueque(truequeSaved,entry.getValue().get(0).getUser(),entry.getKey()));
            entry.getValue().forEach(item ->{
                itemTruequeRepository.save(
                        createItemTrueque(truequeSaved,item));
            });
        });
    }

    private UserTrueque createUserTrueque(Trueque truequeSaved, User user,Integer orden){
       return new UserTrueque(new UserTruequeId(truequeSaved.getId(),user.getId()),orden,false);
    }

    private ItemTrueque createItemTrueque(Trueque truequeSaved,Item item){
        return new ItemTrueque(new ItemTruequeId(truequeSaved.getId(),item.getId()));
    }

}
