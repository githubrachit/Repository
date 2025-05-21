package com.mli.mpro.location.otp.service;

import com.mli.mpro.location.otp.models.FlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class OtpFlowFactory {

    private final Map<FlowType, OtpFlowService> flowMap = new EnumMap<>(FlowType.class);

    @Autowired
    public OtpFlowFactory(Map<String, OtpFlowService> flows) {
        flows.forEach((key, value) -> {
            FlowType flowType = FlowType.valueOf(key.toUpperCase());
            flowMap.put(flowType, value);
        });
    }

    public OtpFlowService getFlow(FlowType flowType) {
        return flowMap.get(flowType);
    }
}
