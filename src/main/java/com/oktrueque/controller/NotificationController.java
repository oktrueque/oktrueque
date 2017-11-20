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

import java.security.Principal;
import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(Principal principal){
        return new ResponseEntity<>(notificationService.getNotificationsByUsername(principal.getName()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notifications/clear")
    public ResponseEntity<Void> clearNotifications(Principal principal){
        notificationService.clearNotifications(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
