package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Map;

@Document(collection = "productRestrictionMasterData")
public class ProductRestrictionMasterData {

    @Id
    private String id;
    private String type;
    private Map<String, ArrayList<Date>> dataMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, ArrayList<Date>> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, ArrayList<Date>> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "ProductRestrictionMasterData{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", dataMap=" + dataMap +
                '}';
    }
}
