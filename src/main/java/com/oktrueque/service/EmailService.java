package com.oktrueque.service;

import com.oktrueque.model.Email;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface EmailService {

    void sendMail(Email email,String template);
}
