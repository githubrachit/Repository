package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

public class Key {
	
	private String label;
	
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Key{" +
				"label='" + label + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
