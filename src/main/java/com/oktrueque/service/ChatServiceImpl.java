package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.service.chat.XmppManager;
import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 8/29/2017.
 */
public class ChatServiceImpl implements ChatService {


    private final XmppManager xmppManager;
    private final UserRepository userRepository;

    public ChatServiceImpl(XmppManager xmppManager, UserRepository userRepository){
        this.xmppManager = xmppManager;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getChatsRegistered() {
        List<User> users = new ArrayList<>();
        xmppManager.getChatsRegistered().forEach(t ->{
            users.add(userRepository.findByUsername(t.getUser()));
        });
        return users;
    }


//    @Override
//    public void loginUser(User user) {
//        AuthenticationToken authenticationToken = new AuthenticationToken
//                (user.getUsername(), user.getUsername()+"pass");
//        RestApiClient restApiClient = new RestApiClient("http://localhost",5222,authenticationToken);
//    }

    @Override
    public void loginIntoXmppService(User user){
        xmppManager.init();
        xmppManager.loginUser(user.getUsername(), user.getUsername()+"pass");
        xmppManager.setStatus(true,"Connected");
    }

    @Override
    public void createNewUser(User user) {
        xmppManager.init();
        xmppManager.createUser(user.getUsername(),user.getUsername()+"pass"
                ,user.getName()+ " " +user.getLast_name(), user.getEmail());
    }
}
