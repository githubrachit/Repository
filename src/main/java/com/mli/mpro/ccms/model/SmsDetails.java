package com.mli.mpro.ccms.model;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@Document(collection = "smsDetails")
public class SmsDetails {

    @Id
    private String id;
    private String documentType;
    private List<String> docId;

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the docId
     */

    public List<String> getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(List<String> docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SmsDetails{" +
                "id='" + id + '\'' +
                ", documentType='" + documentType + '\'' +
                ", docId=" + docId +
                '}';
    }

}