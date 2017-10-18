package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TruequeRepository;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.UserTruequeRepository;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        truequeToSave.setProposalDate(new Date());
        Trueque truequeSaved = truequeRepository.save(truequeToSave);
        saveItemsAndUsers(participants, truequeSaved);
        sendMailTo(participants.get(1).get(0).getUser(),participants.get(2).get(0).getUser(),
                participants.get(1),participants.get(2),truequeSaved);
    }

    @Override
    @Transactional
    public List<User> confirmTruequeAndGetUsersBelongingTo(Long id) {
        Trueque truequeSaved = truequeRepository.findOne(id);
        truequeSaved.setStatus(Constants.TRUEQUE_STATUS_ACTIVE);
        truequeSaved.setAcceptanceDate(new Date());
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(id);
        List<User> users = new ArrayList<>();
        userTrueques.forEach(t ->{
            users.add(userRepository.findOne(t.getId().getUser().getId()));
            if(!t.getStatus().equals(Constants.TRUEQUE_STATUS_ACTIVE)){
                t.setStatus(Constants.TRUEQUE_STATUS_ACTIVE);
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
                new UserTrueque(new UserTruequeId(truequeSaved, user), orden, Constants.TRUEQUE_STATUS_ACTIVE) :
                new UserTrueque(new UserTruequeId(truequeSaved, user), orden, Constants.TRUEQUE_STATUS_PENDING);
        return userTrueque;
    }

    private ItemTrueque createItemTrueque(Trueque truequeSaved, Item item) {
        return new ItemTrueque(new ItemTruequeId(truequeSaved, item));
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
        return userTruequeRepository.findByIdUserId(id);
    }

    @Override
    public List<UserTrueque> getUserTruequeById_TruequeId(long id){
        return userTruequeRepository.findByIdTruequeId(id);
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

    @Override
    public Map<String, Object> getTruequeDetail(Long truequeId) {
        Map map = new LinkedHashMap();
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(truequeId);
        List<ItemTrueque> itemTrueques = itemTruequeRepository.findById_TruequeId(truequeId);
        UserLite user;
        Item item;
        for (UserTrueque ut : userTrueques){
            List<Item> items = new ArrayList<>();
            user = ut.getId().getUser();
            for(ItemTrueque itemTrueque : itemTrueques){
                item = itemTrueque.getId().getItem();
                if(item.getUser().getId().equals(user.getId())){
                    items.add(item);
                }
            }

            map.put(user.getName() + " " + user.getLast_name()
                    , items);
        }
        return map;
    }

    @Override
    public void updateTrueque(Long idTrueque, Integer status) {
        Trueque trueque = truequeRepository.findTruequeById(idTrueque);
        trueque.setStatus(status);
        truequeRepository.save(trueque);
    }

    @Override
    public void deleteItemTrueque(Long idTrueque, Long idUser){
        itemTruequeRepository.deleteAllByIdTruequeIdAndIdItemUserId(idTrueque, idUser);
    }

    @Override
    public void saveItemTrueque(Long idTrueque, List<Long> idItems){
        List<ItemTrueque> itemTrueques = new ArrayList<>();
        idItems.forEach(idItem -> itemTrueques.add(new ItemTrueque(new ItemTruequeId(idTrueque, idItem))));
        itemTruequeRepository.save(itemTrueques);
    }

    @Override
    public boolean isTimeToAsk(Trueque trueque) {
        if (trueque.getStatus().equals("Activo")) {
            if (itsBeenSevenDays(trueque.getAcceptanceDate())) {
                return true;
            }
        }
        return false;
    }

    private boolean itsBeenSevenDays(Date acceptanceDate) {
        long diff = (new Date().getTime() - acceptanceDate.getTime())/(1000 * 60 * 60 * 24);
        if (diff>7) {return true;}
        return false;
        }


    @Override
    @Transactional
    public String updateTrueque(Long idTrueque, List<Long> idItems, Long idUser) {
        //Edit userTrueque
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(idTrueque);
        for(UserTrueque ut : userTrueques){
            if(ut.getStatus().equals(Constants.TRUEQUE_STATUS_CONFIRMED)){
                return String.format("%s ya ha confirmado el trueque, no es posible actualizarlo", ut.getId().getUser().getName());
            }
        }

        //Edit itemTrueque
        itemTruequeRepository.deleteAllByIdTruequeIdAndIdItemUserId(idTrueque, idUser);
        List<ItemTrueque> itemTrueques = new ArrayList<>();
        idItems.forEach(idItem -> itemTrueques.add(new ItemTrueque(new ItemTruequeId(idTrueque, idItem))));
        itemTruequeRepository.save(itemTrueques);

        return null;
    }
}
