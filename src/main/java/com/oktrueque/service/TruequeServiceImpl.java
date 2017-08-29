package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TruequeRepository;
import com.oktrueque.repository.UserTruequeRepository;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Envy on 15/6/2017.
 */

public class TruequeServiceImpl implements TruequeService {


    private final TruequeRepository truequeRepository;
    private final ItemTruequeRepository itemTruequeRepository;
    private final UserTruequeRepository userTruequeRepository;
    private final EmailService emailService;

    public TruequeServiceImpl(TruequeRepository truequeRepository, ItemTruequeRepository itemTruequeRepository,
                              UserTruequeRepository userTruequeRepository, EmailService emailService) {
        this.truequeRepository = truequeRepository;
        this.itemTruequeRepository = itemTruequeRepository;
        this.userTruequeRepository = userTruequeRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void saveTrueque(Map<Integer, List<Item>> participants) {
        Trueque truequeToSave = new Trueque(0);
        truequeToSave.setPeopleCount(participants.size());
        Trueque truequeSaved = truequeRepository.save(truequeToSave);
        saveItemsAndUsers(participants, truequeSaved);
        sendMailTo(participants.get(1).get(0).getUser(),participants.get(2).get(0).getUser(),
                participants.get(1),participants.get(2));
    }

    private void saveItemsAndUsers(Map<Integer, List<Item>> participants, Trueque truequeSaved) {
        participants.entrySet().forEach(entry -> {
            userTruequeRepository.save(
                    createUserTrueque(truequeSaved, entry.getValue().get(0).getUser(), entry.getKey()));
            entry.getValue().forEach(item ->
                    itemTruequeRepository.save(createItemTrueque(truequeSaved, item)));
        });
    }

    private UserTrueque createUserTrueque(Trueque truequeSaved, User user, Integer orden) {
        return new UserTrueque(new UserTruequeId(truequeSaved.getId(), user.getId()), orden, false);
    }

    private ItemTrueque createItemTrueque(Trueque truequeSaved, Item item) {
        return new ItemTrueque(new ItemTruequeId(truequeSaved.getId(), item.getId()));
    }

    private void sendMailTo(User userOrigen, User userDestino, List<Item> itemsPropuestos, List<Item> itemsDemandados){
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
        email.setModel(model);
        emailService.sendMail(email,"truequeRequest.ftl");

    }

    public List<UserTrueque> getUserTruequeById_UserId(long id){
        return userTruequeRepository.getUserTruequeById_UserId(id);
    }

    public List<UserTrueque> getUserTruequeById_TruequeId(long id){
        return userTruequeRepository.getUserTruequeById_TruequeId(id);
    }

    public Trueque getTruequeById (long id){
       return truequeRepository.findTruequeById(id);
    }


}
