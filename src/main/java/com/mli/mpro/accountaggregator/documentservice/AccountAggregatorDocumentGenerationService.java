package com.mli.mpro.accountaggregator.documentservice;

import com.mli.mpro.accountaggregator.FinancialInfoResultResponse;


public interface AccountAggregatorDocumentGenerationService {

	 String generateDocument(FinancialInfoResultResponse financialInfoResult);

}
