package com.mli.mpro.location.services;

import com.mli.mpro.location.models.journeyQuestions.QuestionDetails;
import com.mli.mpro.location.models.questionModels.InputRequest;
import com.mli.mpro.location.models.questionModels.OutPutResponse;
import com.mli.mpro.location.models.questionModels.UTMForQuestions;

import java.util.List;

public interface QuestionDetailsService {
    OutPutResponse<String> saveRecords(InputRequest<QuestionDetails> request);
    OutPutResponse<List<QuestionDetails>> getQuestionDetails(InputRequest<UTMForQuestions> request);
    OutPutResponse<String> removeRecord(InputRequest<QuestionDetails> request);
    OutPutResponse<String> updateRecord(InputRequest<QuestionDetails> request);

}
