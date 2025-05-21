package com.mli.mpro.location.training.model;

public class TrainingResponse {
	
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TrainingResponse [result=" + result + "]";
	}
}
