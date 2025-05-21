package com.mli.mpro.location.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.OutputResponse;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;

@Service
public interface BrmsBrokerService {

    public OutputResponse fetchBrmsResponse(InputRequest inputRequest, String uniqueId, ArrayList<String>errorMessages) throws JsonProcessingException, URISyntaxException, UserHandledException;
}
