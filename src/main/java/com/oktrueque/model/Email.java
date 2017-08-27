package com.oktrueque.model;

import java.util.Map;

/**
 * Created by Facundo on 8/21/2017.
 */
public class Email {

    private String mailTo;

    private String mailSubject;

    private String mailContent;

    private String contentType;

    private Map< String, Object > model;

    public Email() {
        contentType = "text/plain";
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public Map < String, Object > getModel() {
        return model;
    }

    public void setModel(Map < String, Object > model) {
        this.model = model;
    }

}