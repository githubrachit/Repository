package com.mli.mpro.document.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class InfluencerChannelList {
    @Value("${influencerChannel.code}")
    private String influencerChannelCode;
    private List<String> influencerChannelCodeList;
    private static InfluencerChannelList instance;

    @PostConstruct
    public void init() {
        influencerChannelCodeList = splitStringToList(influencerChannelCode);
        instance = this;
    }

    private List<String> splitStringToList(String inputString) {
        List<String> resultList = new ArrayList<>();
        if (inputString != null && !inputString.isEmpty()) {
            resultList = Arrays.asList(inputString.split(","));
        }
        return resultList;
    }

    public List<String> getInfluencerChannelCodeList() {
        return influencerChannelCodeList;
    }

    public static InfluencerChannelList getInstance() {
        return instance;
    }

}
