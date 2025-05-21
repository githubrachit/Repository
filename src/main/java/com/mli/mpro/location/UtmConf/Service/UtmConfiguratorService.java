package com.mli.mpro.location.UtmConf.Service;

import com.mli.mpro.location.UtmConf.models.BrmsInputRequest;
import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorResponse;

public interface UtmConfiguratorService {
    public UtmConfiguratorResponse getUtmCode(BrmsInputRequest utmInputRequest);
}
