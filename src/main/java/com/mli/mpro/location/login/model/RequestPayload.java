package com.mli.mpro.location.login.model;

import java.util.List;

public class RequestPayload {
	
	private List<Transactions> transactions;

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "RequestPayload [transactions=" + transactions + "]";
	}
}
