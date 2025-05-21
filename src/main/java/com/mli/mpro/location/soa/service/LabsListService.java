package com.mli.mpro.location.soa.service;


import com.mli.mpro.location.labslist.models.LabsListRequest;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import org.springframework.http.ResponseEntity;

public interface LabsListService {
	
	 ResponseEntity<Object> executeLabsListService(LabsListRequest labsListRequest) throws SoaCustomException;

}
