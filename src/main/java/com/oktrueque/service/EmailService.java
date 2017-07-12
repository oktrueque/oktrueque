package com.oktrueque.service;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface EmailService {

    void sendMail(String toEmail, String subject, String message);
}
