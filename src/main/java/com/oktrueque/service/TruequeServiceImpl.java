package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TruequeRepository;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.UserTruequeRepository;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Envy on 15/6/2017.
 */

public class TruequeServiceImpl implements TruequeService {

    @Value("${api.url}")
    private String urlServer;


    private final TruequeRepository truequeRepository;
    private final ItemTruequeRepository itemTruequeRepository;
    private final UserTruequeRepository userTruequeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public TruequeServiceImpl(TruequeRepository truequeRepository, ItemTruequeRepository itemTruequeRepository,
                              UserTruequeRepository userTruequeRepository, EmailService emailService, UserRepository userRepository) {
        this.truequeRepository = truequeRepository;
        this.itemTruequeRepository = itemTruequeRepository;
        this.userTruequeRepository = userTruequeRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveTrueque(Map<Integer, List<Item>> participants) {
        Trueque truequeToSave = new Trueque(0);
        truequeToSave.setPeopleCount(participants.size());
        Trueque truequeSaved = truequeRepository.save(truequeToSave);
        saveItemsAndUsers(participants, truequeSaved);
        sendMailTo(participants.get(1).get(0).getUser(),participants.get(2).get(0).getUser(),
                participants.get(1),participants.get(2),truequeSaved);
    }

    @Override
    @Transactional
    public List<User> confirmTruequeAndGetUsersBelongingTo(Long id) {
        Trueque truequeSaved = truequeRepository.findOne(id);
        truequeSaved.setStatus(1);
        truequeSaved.setAcceptanceDate(LocalDateTime.now());
        List<UserTrueque> userTrueques = userTruequeRepository.getUserTruequeById_TruequeId(id);
        List<User> users = new ArrayList<>();
        userTrueques.forEach(t ->{
            users.add(userRepository.findOne(t.getId().getUserId()));
            if(!t.isConfirm()){
                t.setConfirm(true);
            }
        });
        return users;
    }

    @Override
    public void updateTrueque(Trueque trueque) {
        truequeRepository.save(trueque);
    }


    private void saveItemsAndUsers(Map<Integer, List<Item>> participants, Trueque truequeSaved) {
        participants.entrySet().forEach(entry -> {
            userTruequeRepository.save(
                    createUserTrueque(truequeSaved, entry.getValue().get(0).getUser(), entry.getKey()));
            entry.getValue().forEach(item ->
                    itemTruequeRepository.save(createItemTrueque(truequeSaved, item)));
        });
    }

    private UserTrueque createUserTrueque(Trueque truequeSaved, UserLite user, Integer orden) {
        UserTrueque userTrueque = (orden.equals(1)) ?
                new UserTrueque(new UserTruequeId(truequeSaved.getId(), user.getId()), orden, true) :
                new UserTrueque(new UserTruequeId(truequeSaved.getId(), user.getId()), orden, false);
        return userTrueque;
    }

    private ItemTrueque createItemTrueque(Trueque truequeSaved, Item item) {
        return new ItemTrueque(new ItemTruequeId(truequeSaved.getId(), item.getId()));
    }

    private void sendMailTo(UserLite userOrigen, UserLite userDestino,
                            List<Item> itemsPropuestos, List<Item> itemsDemandados, Trueque trueque){
        Email email = new Email();
        email.setMailTo(userDestino.getEmail());
        email.setMailSubject("Nueva propuesta de Trueque");
        Map< String, Object > model = new LinkedHashMap<>();
        model.put("nombreOrigen", userOrigen.getName());
        model.put("apellidoOrigen", userOrigen.getLast_name());
        model.put("nombreDestino", userDestino.getName());
        model.put("apellidoDestino", userDestino.getLast_name());
        model.put("itemsPropuestos", itemsPropuestos);
        model.put("itemsDemandados", itemsDemandados);
        model.put("uri_confirm",urlServer + "trueques/"+trueque.getId()+"/accept");
        email.setModel(model);
        emailService.sendMail(email,"truequeRequest.ftl");

    }

    @Override
    public List<UserTrueque> getUserTruequeById_UserId(long id){
        return userTruequeRepository.getUserTruequeById_UserId(id);
    }

    @Override
    public List<UserTrueque> getUserTruequeById_TruequeId(long id){
        return userTruequeRepository.getUserTruequeById_TruequeId(id);
    }

    @Override
    public Trueque getTruequeById (long id){
       return truequeRepository.findTruequeById(id);
    }

    @Override
    public List<ItemTrueque> getItemsTruequeById_TruequeId(long id){
        return itemTruequeRepository.findById_TruequeId(id);
    }

    @Override
    public Trueque findTruequeByIdAndStatusIsNotIn(Long id, int[] statuses) {
        return truequeRepository.findTruequeByIdAndStatusIsNotIn(id, statuses);
    }
}
