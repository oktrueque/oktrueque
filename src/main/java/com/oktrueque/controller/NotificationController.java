package com.oktrueque.controller;

import com.oktrueque.model.Notification;
import com.oktrueque.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notifications/{username}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String username){
        return new ResponseEntity<>(notificationService.getNotificationsByUsername(username), HttpStatus.OK);
    }
}
