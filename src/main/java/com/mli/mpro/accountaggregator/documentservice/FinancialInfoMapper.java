package com.mli.mpro.accountaggregator.documentservice;

import com.mli.mpro.accountaggregator.FinancialInfoResultResponse;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class FinancialInfoMapper {

	public Context setIncomeInfo(FinancialInfoResultResponse financialInfoResult) {
		
		Context context = new Context();
		Map<String, Object> dataForDocument = new HashMap<>();
		dataForDocument.put("incomeInfo", financialInfoResult.getData().getIncomeInfo());
		
		context.setVariables(dataForDocument);
		return context;
	}

	public Context setExpenseInfo(FinancialInfoResultResponse financialInfoResult) {
		
		Context context = new Context();
		Map<String, Object> dataForDocument = new HashMap<>();
		dataForDocument.put("expenseInfo", financialInfoResult.getData().getExpenseInfo());
		
		context.setVariables(dataForDocument);
		return context;
	}

	public Context setMainContext(String processedHTMLIncomeInfo, String processedHTMLExpenseInfo) {
		
		Context context = new Context();
		Map<String, Object> dataForDocument = new HashMap<>();
		dataForDocument.put("incomeInfo", processedHTMLIncomeInfo);
		dataForDocument.put("expenseInfo", processedHTMLExpenseInfo);
		
		context.setVariables(dataForDocument);
		return context;
	}

}
