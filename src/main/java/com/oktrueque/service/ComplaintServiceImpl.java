package com.oktrueque.service;

import com.oktrueque.model.Complaint;
import com.oktrueque.repository.ComplaintRepository;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;


    public ComplaintServiceImpl(ComplaintRepository complaintRepository){
        this.complaintRepository = complaintRepository;
    }


    @Override
    public void addComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }
}
