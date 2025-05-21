package com.mli.mpro.accountaggregator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

import java.util.Date;
import java.util.List;

public class FinancialInfoResultResponse {

	private String flowId;
	private String runId;
	private String status;
	private Data data;

	public String getFlowId() {
		return flowId;
	}

	public FinancialInfoResultResponse setFlowId(String flowId) {
		this.flowId = flowId;
		return this;
	}

	public String getRunId() {
		return runId;
	}

	public FinancialInfoResultResponse setRunId(String runId) {
		this.runId = runId;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public FinancialInfoResultResponse setStatus(String status) {
		this.status = status;
		return this;
	}

	public Data getData() {
		return data;
	}

	public FinancialInfoResultResponse setData(
			Data data) {
		this.data = data;
		return this;
	}

	public static class Data {

		private IncomeInfo incomeInfo;
		private ExpenseInfo expenseInfo;
		private Meta meta;

		public IncomeInfo getIncomeInfo() {
			return incomeInfo;
		}

		public Data setIncomeInfo(
				IncomeInfo incomeInfo) {
			this.incomeInfo = incomeInfo;
			return this;
		}

		public ExpenseInfo getExpenseInfo() {
			return expenseInfo;
		}

		public Data setExpenseInfo(
				ExpenseInfo expenseInfo) {
			this.expenseInfo = expenseInfo;
			return this;
		}

		public Meta getMeta() {
			return meta;
		}

		public Data setMeta(Meta meta) {
			this.meta = meta;
			return this;
		}

		public static class IncomeInfo {

			private SalaryInfo salaryInfo;
			private CashCredits cashCredits;
			private NonCashCredits nonCashCredits;

			public SalaryInfo getSalaryInfo() {
				return salaryInfo;
			}

			public IncomeInfo setSalaryInfo(
					SalaryInfo salaryInfo) {
				this.salaryInfo = salaryInfo;
				return this;
			}

			public CashCredits getCashCredits() {
				return cashCredits;
			}

			public IncomeInfo setCashCredits(
					CashCredits cashCredits) {
				this.cashCredits = cashCredits;
				return this;
			}

			public NonCashCredits getNonCashCredits() {
				return nonCashCredits;
			}

			public IncomeInfo setNonCashCredits(
					NonCashCredits nonCashCredits) {
				this.nonCashCredits = nonCashCredits;
				return this;
			}

			public static class SalaryInfo {

				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
				private List<Transaction> transactions = null;
			}


			public static class CashCredits {

				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
				private List<Transaction> transactions = null;
			}


			public static class NonCashCredits {

				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
				private Details details;
				private List<Transaction> transactions = null;


				public static class Details {

					private Integer totalInsuranceClaim;
					private Integer totalInvestmentRedemption;
					private Integer avgMonthlyRental;
					private Integer totalRental;
				}
			}
		}


		public static class Transaction {
			private String linkedAccRef;
			private String maskedAccNumber;
			@JsonProperty(value = "account_type")
			private String accountType;
			private Date transactionTimestamp;
			private Date valueDate;
			private String txnId;
			private String type;
			private String mode;
			private String narration;
			@Sensitive(MaskType.AMOUNT)
			private Double amount;
			private Double currentBalance;
			private String reference;
		}


		public static class ExpenseInfo {

			private Summary summary;
			private HealthAndFitness healthAndFitness;
			private InvestmentAndInsurance investmentAndInsurance;


			public static class Summary {
				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
			}


			public static class HealthAndFitness {

				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
				private SpecificExpenditure specificExpenditure;
				private List<Transaction> transactions = null;
			}


			public static class SpecificExpenditure {
				private Integer totalMFAndEquity;
				private Integer avgMonthlyMFAndEquity;
				private Integer totalInsurancePaid;
				private Integer totalInsurancePaidTransactions;
				
				private Integer totalHospitalAndMedical;
				private Integer avgMonthlyHospitalAndMedical;
			}


			public static class InvestmentAndInsurance {

				private Integer total;
				private Integer avgMonthly;
				private Integer minMonthly;
				private Integer maxMonthly;
				private Integer totalTransactions;
				private SpecificExpenditure specificExpenditure;
				private List<Transaction> transactions = null;
			}
		}


		public static class Meta {

			private Duration duration;
			private List<Account> accounts = null;


			public static class Duration {

				private String from;
				private String to;
			}


			public static class Account {

				private String maskedAccountNumber;
				private String linkedAccountNumber;
				private Integer totalTransactions;
			}
		}
	}

}
