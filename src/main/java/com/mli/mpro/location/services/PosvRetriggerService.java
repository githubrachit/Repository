package com.mli.mpro.location.services;

import com.mli.mpro.location.ivc.request.InputRequest;
import com.mli.mpro.location.ivc.response.OutputResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface PosvRetriggerService {

 public OutputResponse callPosvRetriggerApi(InputRequest inputRequest, ArrayList<String> errorMessages);

}
