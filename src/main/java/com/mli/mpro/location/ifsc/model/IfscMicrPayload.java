package com.mli.mpro.location.ifsc.model;

import java.util.List;

public class IfscMicrPayload {
	
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "IfscMicrPayload [transactions=" + transactions + "]";
	}
	
}
