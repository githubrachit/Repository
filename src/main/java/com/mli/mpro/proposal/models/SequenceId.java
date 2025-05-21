package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
public class SequenceId {
    @Id
    private String id;

    private long sequence;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public long getSequence() {
	return sequence;
    }

    public void setSequence(long sequence) {
	this.sequence = sequence;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "SequenceId [id=" + id + ", sequence=" + sequence + "]";
    }
}