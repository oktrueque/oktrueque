package com.oktrueque.service;

import com.oktrueque.model.Complaint;
import com.oktrueque.model.Email;
import com.oktrueque.repository.ComplaintRepository;
import com.oktrueque.repository.ComplaintTypeRepository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final EmailService emailService;
    private final ComplaintTypeRepository complaintTypeRepository;


    public ComplaintServiceImpl(ComplaintRepository complaintRepository, EmailService emailService, ComplaintTypeRepository complaintTypeRepository) {
        this.complaintRepository = complaintRepository;
        this.emailService = emailService;
        this.complaintTypeRepository = complaintTypeRepository;
    }

    @Override
    public void saveComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
        sendMailTo(complaint);
    }

    private void sendMailTo(Complaint complaint){
        Email email = new Email();
        email.setMailTo("oktrueque@gmail.com");
        email.setMailSubject("Nueva denuncia!");
        Map< String, Object > model = new LinkedHashMap<>();
        model.put("userTarget", complaint.getUser_target());
        model.put("userOrigin", complaint.getUser_origin());
        model.put("complaintType", complaintTypeRepository.getById(complaint.getComplaintType().getId()));
        model.put("complaint", complaint);
        email.setModel(model);
        emailService.sendMail(email,"newComplaint.ftl");
    }
}
