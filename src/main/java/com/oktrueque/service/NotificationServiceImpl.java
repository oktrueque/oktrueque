package com.oktrueque.service;

import com.oktrueque.model.Message;
import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public class NotificationServiceImpl implements NotificationService{

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendTruequeAcceptedNotification(List<String> usernames, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("Trueque Aceptado!");
        user.setPhoto1(userOrigin.getPhoto1());
        for(String username : usernames){
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                    new Message(userOrigin.getName() + " ha aceptado el trueque propuesto, se ha abierto un chat en la pestaña " +
                            "Mensajes para que puedan ponerse de acuerdo para realizar el trueque", user));
        }
    }
}
