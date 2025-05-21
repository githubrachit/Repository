package com.mli.mpro.email.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "emailDetails")
public class EmailDetails {

    @Id
    private String id;
    private String documentType;
    @Sensitive(MaskType.EMAIL)
    private String mailBody;

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
     * @return the mailBody
     */
    public String getMailBody() {
	return mailBody;
    }

    /**
     * @param mailBody the mailBody to set
     */
    public void setMailBody(String mailBody) {
	this.mailBody = mailBody;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "EmailDetails [documentType=" + documentType + ", mailBody=" + mailBody + "]";
    }

}
