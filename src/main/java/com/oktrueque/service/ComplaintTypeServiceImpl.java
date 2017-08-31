package com.oktrueque.service;

import com.oktrueque.model.ComplaintType;
import com.oktrueque.repository.ComplaintTypeRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */
public class ComplaintTypeServiceImpl implements ComplaintTypeService {

    private final ComplaintTypeRepository complaintTypeRepository;

    public ComplaintTypeServiceImpl(ComplaintTypeRepository complaintTypeRepository) {
        this.complaintTypeRepository = complaintTypeRepository;
    }

    @Override
    public List<ComplaintType> getComplaintTypes() {

             return complaintTypeRepository.findAll();

    }
}
