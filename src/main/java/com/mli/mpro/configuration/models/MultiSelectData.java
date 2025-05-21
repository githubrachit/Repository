package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "multiselectdata")
public class MultiSelectData {
    @Id
    private String id;
    private String key;
    private List<String> values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MultiSelectData{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", values=" + values +
                '}';
    }
}
