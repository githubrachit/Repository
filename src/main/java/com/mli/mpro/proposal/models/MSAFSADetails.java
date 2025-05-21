package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class MSAFSADetails {

	private double valueOfMSA;

	private double valueOfFSA;

	private double valueOfAFYP;

	private double valueOfDD;

	private double valueOfSUC;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured1;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured2;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured3;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured4;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured5;
	@Sensitive(MaskType.AMOUNT)
	private double sumAssured6;//103507 AI03 grid changes

	/**
	 * @return the valueOfMSA
	 */
	public double getValueOfMSA() {
		return valueOfMSA;
	}

	/**
	 * @param mSA the valueOfMSA to set
	 */
	public void setValueOfMSA(double mSA) {
		this.valueOfMSA = mSA;
	}

	/**
	 * @return the valueOfFSA
	 */
	public double getValueOfFSA() {
		return valueOfFSA;
	}

	/**
	 * @param valueOfFSA the valueOfFSA to set
	 */
	public void setValueOfFSA(double valueOfFSA) {
		this.valueOfFSA = valueOfFSA;
	}

	/**
	 * @return the valueOfAFYP
	 */
	public double getValueOfAFYP() {
		return valueOfAFYP;
	}

	/**
	 * @param valueOfAFYP the valueOfAFYP to set
	 */
	public void setValueOfAFYP(double valueOfAFYP) {
		this.valueOfAFYP = valueOfAFYP;
	}

	/**
	 * @return the valueOfDD
	 */
	public double getValueOfDD() {
		return valueOfDD;
	}

	/**
	 * @param valueOfDD the valueOfDD to set
	 */
	public void setValueOfDD(double valueOfDD) {
		this.valueOfDD = valueOfDD;
	}

	public double getValueOfSUC() {
		return valueOfSUC;
	}

	public void setValueOfSUC(double valueOfSUC) {
		this.valueOfSUC = valueOfSUC;
	}

	public double getSumAssured1() {
		return sumAssured1;
	}

	public void setSumAssured1(double sumAssured1) {
		this.sumAssured1 = sumAssured1;
	}

	public double getSumAssured2() {
		return sumAssured2;
	}

	public void setSumAssured2(double sumAssured2) {
		this.sumAssured2 = sumAssured2;
	}

	public double getSumAssured3() {
		return sumAssured3;
	}

	public void setSumAssured3(double sumAssured3) {
		this.sumAssured3 = sumAssured3;
	}

	public double getSumAssured4() {
		return sumAssured4;
	}

	public void setSumAssured4(double sumAssured4) {
		this.sumAssured4 = sumAssured4;
	}

	public double getSumAssured5() {
		return sumAssured5;
	}

	public void setSumAssured5(double sumAssured5) {
		this.sumAssured5 = sumAssured5;
	}
	//103507 AI03 grid changes
	public double getSumAssured6() {
		return sumAssured6;
	}
	//103507 AI03 grid changes
	public void setSumAssured6(double sumAssured6) {
		this.sumAssured6 = sumAssured6;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MSAFSADetails [valueOfMSA=" + valueOfMSA + ", valueOfFSA=" + valueOfFSA + ", valueOfAFYP=" + valueOfAFYP
				+ ", valueOfDD=" + valueOfDD + ", valueOfSUC=" + valueOfSUC + "sumAssured1=" + sumAssured1
				+ "sumAssured2=" + sumAssured2 + "sumAssured3=" + sumAssured3 + "sumAssured4=" + sumAssured4
				+ "sumAssured5=" + sumAssured5 + "sumAssured6=" + sumAssured6 +"]";
	}
}
