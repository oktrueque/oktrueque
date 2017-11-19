package com.oktrueque.service;

import com.oktrueque.model.Message;
import com.oktrueque.model.Notification;
import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;
import com.oktrueque.repository.NotificationRepository;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements NotificationService{

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate, NotificationRepository notificationRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendTruequeAcceptedNotification(List<String> usernames, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("¡Trueque Aceptado!");
        user.setPhoto1(userOrigin.getPhoto1());
        List<Notification> notifications = new ArrayList<>();
        for(String username : usernames){
            notifications.add(new Notification(username, Constants.NOTIFICATION_TRUEQUE_ACCEPTED_KEY));
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                    new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_ACCEPTED_KEY), userOrigin.getName()), user));
        }
        this.notificationRepository.save(notifications);
    }

    @Override
    public void sendTruequeModifiedForItemTrocadoNotification(List<String> usernames, UserLite userOrigin) {
        UserLite user = new UserLite();
        user.setName("Trueque Modificado");
        user.setPhoto1(userOrigin.getPhoto1());
        List<Notification> notifications = new ArrayList<>();
        for(String username : usernames){
            notifications.add(new Notification(username, Constants.NOTIFICATION_TRUEQUE_MODIFIED_CAUSE_CONFIRM_KEY));
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                    new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_MODIFIED_CAUSE_CONFIRM_KEY), userOrigin.getName()), user));
        }
        this.notificationRepository.save(notifications);
    }

    @Override
    public void sendTruequeAcceptedByMeNotification(String username, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("¡Trueque Aceptado!");
        user.setPhoto1(Constants.IMG_LOGO_OKTRUEQUE);
        simpMessagingTemplate.convertAndSendToUser(userOrigin.getUsername(), "/queue/notification",
                new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_ACCEPTED_BY_ME_KEY), userOrigin.getName()), user));
        this.notificationRepository.save(new Notification(username, Constants.NOTIFICATION_TRUEQUE_ACCEPTED_BY_ME_KEY));
    }

    @Override
    public void sendTruequeProposedNotification(String username, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("¡Trueque Propuesto!");
        user.setPhoto1(userOrigin.getPhoto1());
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_PROPOSED_KEY), userOrigin.getName()), user));
        this.notificationRepository.save(new Notification(username, Constants.NOTIFICATION_TRUEQUE_PROPOSED_KEY));
    }

    @Override
    public void sendTruequeCanceledByUser(List<String> usernames, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("Trueque Cancelado");
        user.setPhoto1(userOrigin.getPhoto1());
        List<Notification> notifications = new ArrayList<>();
        for(String username : usernames){
            notifications.add(new Notification(username, Constants.NOTIFICATION_TRUEQUE_CANCELED_KEY));
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                    new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_CANCELED_KEY), userOrigin.getName()), user));
        }
        this.notificationRepository.save(notifications);
    }

    @Override
    public void sendTruequeRejectedByUser(List<String> usernames, User userOrigin) {
        UserLite user = new UserLite();
        user.setName("Trueque Rechazado");
        user.setPhoto1(userOrigin.getPhoto1());
        List<Notification> notifications = new ArrayList<>();
        for(String username : usernames){
            notifications.add(new Notification(username, Constants.NOTIFICATION_TRUEQUE_REJECTED_KEY));
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification",
                    new Message(String.format(Constants.getNotificationMessage(Constants.NOTIFICATION_TRUEQUE_REJECTED_KEY), userOrigin.getName()), user));
        }
        this.notificationRepository.save(notifications);
    }
}
