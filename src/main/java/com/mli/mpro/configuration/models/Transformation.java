package com.mli.mpro.configuration.models;


import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashMap;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@Document(collection = "transformation")
public class Transformation {

    private String channel;
    private String fieldToTransform;
    private HashMap<String, String> transformedMap;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFieldToTransform() {
        return fieldToTransform;
    }

    public void setFieldToTransform(String fieldToTransform) {
        this.fieldToTransform = fieldToTransform;
    }

    public HashMap<String, String> getTransformedMap() {
        return transformedMap;
    }

    public void setTransformedMap(HashMap<String, String> transformedMap) {
        this.transformedMap = transformedMap;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Transformation{" +
                "channel='" + channel + '\'' +
                ", fieldToTransform='" + fieldToTransform + '\'' +
                ", transformedMap=" + transformedMap +
                '}';
    }
}
