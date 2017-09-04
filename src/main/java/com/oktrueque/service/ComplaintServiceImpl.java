package com.oktrueque.service;

import com.oktrueque.model.Complaint;
import com.oktrueque.model.Email;
import com.oktrueque.model.User;
import com.oktrueque.repository.ComplaintRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final EmailService emailService;


    public ComplaintServiceImpl(ComplaintRepository complaintRepository, EmailService emailService) {
        this.complaintRepository = complaintRepository;
        this.emailService = emailService;
    }

    @Override
    public void saveComplaint(Complaint complaint) {

        complaintRepository.save(complaint);
        sendMailTo(complaint);

    }

    private void sendMailTo(Complaint complaint){
        Email email = new Email();
        email.setMailTo("fabrisposetti10@gmail.com");
        email.setMailSubject("Nueva denuncia!");

        Map< String, Object > model = new LinkedHashMap<>();
        model.put("denunciado", complaint.getUser_target());
        model.put("denunciante", complaint.getUser_origin());
        model.put("denuncia", complaint);


     // model.put("uri_confirm","http://localhost:8080/trueque/"+trueque.getId()+"/confirm");
        email.setModel(model);
        emailService.sendMail(email,"newComplaint.ftl");

    }
}
