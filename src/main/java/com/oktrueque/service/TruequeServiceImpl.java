package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TruequeRepository;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.UserTruequeRepository;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.util.*;

public class TruequeServiceImpl implements TruequeService {

    @Value("${api.url}")
    private String urlServer;


    private final TruequeRepository truequeRepository;
    private final ItemTruequeRepository itemTruequeRepository;
    private final UserTruequeRepository userTruequeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConversationService conversationService;

    public TruequeServiceImpl(TruequeRepository truequeRepository, ItemTruequeRepository itemTruequeRepository,
                              UserTruequeRepository userTruequeRepository, EmailService emailService, UserRepository userRepository, ConversationService conversationService) {
        this.truequeRepository = truequeRepository;
        this.itemTruequeRepository = itemTruequeRepository;
        this.userTruequeRepository = userTruequeRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.conversationService = conversationService;
    }

    @Override
    @Transactional
    public void saveTrueque(Map<Integer, List<Item>> participants, String username) {
        Trueque truequeToSave = new Trueque(0);
        truequeToSave.setPeopleCount(participants.size());
        truequeToSave.setProposalDate(new Date());
        Trueque truequeSaved = truequeRepository.save(truequeToSave);
        saveItemsAndUsers(participants, truequeSaved, username);
        sendMailTo(participants.get(1).get(0).getUser(),participants.get(2).get(0).getUser(),
                participants.get(1),participants.get(2),truequeSaved);
    }

    @Override
    @Transactional
    public void acceptTruequeAndGetUsersBelongingTo(Long id, String username) {
        Trueque truequeSaved = truequeRepository.findOne(id);
        if(!this.updateUserTruequeStatus(truequeSaved, username, Constants.TRUEQUE_STATUS_ACTIVE)){
            truequeSaved.setStatus(Constants.TRUEQUE_STATUS_ACTIVE);
            truequeSaved.setAcceptanceDate(new Date());
            truequeRepository.save(truequeSaved);
            this.createChat(truequeSaved);
        }
    }

    @Override
    @Transactional
    public List<UserLite> confirmTrueque(Long id, String username) {
        Trueque truequeSaved = truequeRepository.findOne(id);
        List<UserLite> users = new ArrayList<>();
        List<UserTrueque> ut = userTruequeRepository.findByIdTruequeId(id);
        for(UserTrueque userTrueque : ut){
            if(!userTrueque.getId().getUser().getUsername().equals(username)){
                users.add(userTrueque.getId().getUser());
            }
        }

        if(!this.updateUserTruequeStatus(truequeSaved, username, Constants.TRUEQUE_STATUS_CONFIRMED)){
            truequeSaved.setStatus(Constants.TRUEQUE_STATUS_CONFIRMED);
            truequeSaved.setEndingDate(new Date());
            truequeRepository.save(truequeSaved);
            this.deleteChat(truequeSaved);
        }

        return users;
    }

    private Boolean updateUserTruequeStatus(Trueque trueque, String username, Integer status){
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(trueque.getId());
        Boolean usersLeft = false;
        UserLite me = null;
        for(UserTrueque ut : userTrueques){
            if(ut.getId().getUser().getUsername().equals(username)){
                ut.setStatus(status);
                ut.setShowActions(false);
                userTruequeRepository.save(ut);
                me = ut.getId().getUser();
            }
            if(ut.getStatus() < status){
                usersLeft = true;
            }
        }
        if(!usersLeft){
            for(UserTrueque ut : userTrueques){
                ut.setShowActions(true);
                userTruequeRepository.save(ut);
                if(!ut.getId().getUser().getUsername().equals(username)){
                    this.sendUpdateTruequeMail(trueque, me, ut.getId().getUser(), status);
                }
            }
        }
        return usersLeft;
    }

    private void sendUpdateTruequeMail(Trueque trueque, UserLite me, UserLite userTarget, Integer status){
        if(status == Constants.TRUEQUE_STATUS_ACTIVE){
            sendAcceptTruequeMail(trueque, me, userTarget);
        }
        if(status == Constants.TRUEQUE_STATUS_CONFIRMED){
            sendConfirmTruequeMail(trueque, me, userTarget);
        }
    }

    private void sendAcceptTruequeMail(Trueque trueque, UserLite me, UserLite userTarget){
        String url = urlServer + "profile/conversations";
        Email emailObject = new Email();
        String email=userTarget.getEmail();
        Map<String,Object> model = new LinkedHashMap<>();
        emailObject.setMailTo(email);
        emailObject.setMailSubject("OkTrueque - Trueque aceptado!");
        model.put("trueque",trueque);
        model.put("me",me);
        model.put("userTarget",userTarget);
        model.put("url",url);
        emailObject.setModel(model);
        emailService.sendMail(emailObject,"truequeAceptado.ftl");
    }

    private void sendConfirmTruequeMail(Trueque trueque, UserLite me, UserLite userTarget){
        String url = urlServer + "profile/conversations";
        Email emailObject = new Email();
        String email=userTarget.getEmail();
        Map<String,Object> model = new LinkedHashMap<>();
        emailObject.setMailTo(email);
        emailObject.setMailSubject("OkTrueque - Trueque confirmado!");
        model.put("trueque",trueque);
        model.put("me",me);
        model.put("userTarget",userTarget);
        model.put("url",url);
        emailObject.setModel(model);
        emailService.sendMail(emailObject,"truequeConfirmado.ftl");
    }

    private void createChat(Trueque trueque){
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(trueque.getId());
        conversationService.createConversation(trueque, userTrueques);
    }

    private void deleteChat(Trueque trueque){
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(trueque.getId());
        conversationService.deleteConversation(trueque, userTrueques);
    }

    @Override
    public void updateTrueque(Trueque trueque) {
        truequeRepository.save(trueque);
    }


    private void saveItemsAndUsers(Map<Integer, List<Item>> participants, Trueque truequeSaved, String username) {
        participants.entrySet().forEach(entry -> {
            if(entry.getValue().get(0).getUser().getUsername().equals(username)){
                userTruequeRepository.save(
                        createUserTrueque(truequeSaved, entry.getValue().get(0).getUser(), entry.getKey(), false));
            }else{
                userTruequeRepository.save(
                        createUserTrueque(truequeSaved, entry.getValue().get(0).getUser(), entry.getKey(), true));
            }

            entry.getValue().forEach(item ->
                    itemTruequeRepository.save(createItemTrueque(truequeSaved, item)));
        });
    }

    private UserTrueque createUserTrueque(Trueque truequeSaved, UserLite user, Integer orden, Boolean showActions) {
        UserTrueque userTrueque = (orden.equals(1)) ?
                new UserTrueque(new UserTruequeId(truequeSaved, user), orden, Constants.TRUEQUE_STATUS_ACTIVE, showActions) :
                new UserTrueque(new UserTruequeId(truequeSaved, user), orden, Constants.TRUEQUE_STATUS_PENDING, showActions);
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
        model.put("uri_confirm",urlServer + "trueques/"+trueque.getId()+"/user/"+ userDestino.getUsername() +"/accept");
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
        sendMailTruequeEdit(idTrueque,idUser);
        return null;
    }

    private void sendMailTruequeEdit(Long idTrueque, Long idUser){
        String url = urlServer + "profile/trueques/" + idTrueque;
        Email emailObject = new Email();
        User user = userRepository.findUserById(idUser);
        List<UserTrueque> userTrueques = userTruequeRepository.findByIdTruequeId(idTrueque);
        String userTargetEmail = "";
        Trueque trueque = new Trueque();

        for (UserTrueque UT: userTrueques) {
            if(UT.getId().getUser().getId()!=idUser){
                userTargetEmail = UT.getId().getUser().getEmail();
                trueque = UT.getId().getTrueque();
            }
        }
        Map<String,Object> model = new LinkedHashMap<>();
        emailObject.setMailTo(userTargetEmail);
        emailObject.setMailSubject("OkTrueque - Trueque editado");
        model.put("trueque",trueque);
        model.put("userWhoEdits",user.getName() + " " + user.getLast_name());
        model.put("url",url);
        emailObject.setModel(model);
        emailService.sendMail(emailObject,"truequeEdited.ftl");
    }
}
