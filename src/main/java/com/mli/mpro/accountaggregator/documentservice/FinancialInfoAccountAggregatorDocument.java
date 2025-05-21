package com.mli.mpro.accountaggregator.documentservice;

import com.mli.mpro.accountaggregator.FinancialInfoResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component("financialInfoDocument")
public class FinancialInfoAccountAggregatorDocument implements AccountAggregatorDocumentGenerationService {

	 @Autowired
	 private SpringTemplateEngine springTemplateEngine;
	
	 @Autowired
	 private FinancialInfoMapper financialInfoMapper;
	 
	 @Autowired
	 private AccountAggregatorDocumentHelper accountAggregatorDocumentHelper;
	 
	@Override
	public String generateDocument(FinancialInfoResultResponse financialInfoResult) {
		
		String processedHTMLIncomeInfo = null;
		String processedHTMLExpenseInfo = null;
		String mainProcessedHTML = null;
		
		Context incomeInfoContext = financialInfoMapper.setIncomeInfo(financialInfoResult);
		Context expenseInfoContext = financialInfoMapper.setExpenseInfo(financialInfoResult);
		
		processedHTMLIncomeInfo = springTemplateEngine.process("financialInfo\\incomeInfo", incomeInfoContext);
		processedHTMLExpenseInfo = springTemplateEngine.process("financialInfo\\expenseInfo", expenseInfoContext);
		
		Context mainContext = financialInfoMapper.setMainContext(processedHTMLIncomeInfo, processedHTMLExpenseInfo);
		
		mainProcessedHTML = springTemplateEngine.process("financialInfo\\main", mainContext);

		return accountAggregatorDocumentHelper.generateDocument(mainProcessedHTML, 0);
	}
}
