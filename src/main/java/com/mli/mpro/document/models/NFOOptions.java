package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

/**
 * @author akshom4375
 *
 */
public class NFOOptions {
	
	private List<Key> keys;

	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "NFOOptions{" +
				"keys=" + keys +
				'}';
	}
}
