package com.mli.mpro.location.services.impl;

import com.mli.mpro.location.models.TransformationMasterDetails;
import com.mli.mpro.location.repository.TransformationMasterDetailsRepository;
import com.mli.mpro.location.services.TransformationMasterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransformationMasterDetailsServiceImpl implements TransformationMasterDetailService {

    @Autowired
    private TransformationMasterDetailsRepository transformationMasterDetailsRepository;

    @Override
    public List<TransformationMasterDetails> allMasterDeatils() {
        return transformationMasterDetailsRepository.findAll() ;
    }
}
