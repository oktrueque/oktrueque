package com.oktrueque.utils;

import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 * Created by Envy on 17/6/2017.
 */
public class Encrypter {

    private BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

    public BasicPasswordEncryptor getEncryptor() {
        return encryptor;
    }

    public void setEncryptor(BasicPasswordEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encrypt(String password){
        return encryptor.encryptPassword(password);
    }

    public boolean checkPassword(String plainPassword){
        return encryptor.checkPassword(plainPassword, this.encrypt(plainPassword));
    }

}
