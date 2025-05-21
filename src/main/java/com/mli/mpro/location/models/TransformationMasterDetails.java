package com.mli.mpro.location.models;

import java.util.HashMap;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@Document(collection = "masterdata")
public class TransformationMasterDetails {

	@Id
	private String id;

	@JsonProperty("xmlTag")
	private String xmlTag;

	@JsonProperty("jsonTag")
	private String jsonTag;

	@JsonProperty("metadata")
	private HashMap<String, String> metadata;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXmlTag() {
		return xmlTag;
	}

	public void setXmlTag(String xmlTag) {
		this.xmlTag = xmlTag;
	}

	public String getJsonTag() {
		return jsonTag;
	}

	public void setJsonTag(String jsonTag) {
		this.jsonTag = jsonTag;
	}

	public HashMap<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TransformationMasterDetails [id=" + id + ", xmlTag=" + xmlTag + ", jsonTag=" + jsonTag + ", metadata="
				+ metadata + "]";
	}
}
