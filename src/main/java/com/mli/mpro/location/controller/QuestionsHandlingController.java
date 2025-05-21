package com.mli.mpro.location.controller;

import com.mli.mpro.common.models.MsgInfo;
import com.mli.mpro.location.models.journeyQuestions.QuestionDetails;
import com.mli.mpro.location.models.questionModels.InputRequest;
import com.mli.mpro.location.models.questionModels.OutPutResponse;
import com.mli.mpro.location.models.questionModels.Response;
import com.mli.mpro.location.models.questionModels.UTMForQuestions;
import com.mli.mpro.location.services.QuestionDetailsService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST;

@RestController
@RequestMapping(path = "/locationservices/questions")
public class QuestionsHandlingController {
    private static final Logger logger= LoggerFactory.getLogger(QuestionsHandlingController.class);
    QuestionDetailsService questionDetailsService;
    @Autowired
    public QuestionsHandlingController(QuestionDetailsService questionDetailsService) {
        this.questionDetailsService = questionDetailsService;
    }

    @PostMapping(path = "/save")
    public OutPutResponse<String> saveNewRecords(@RequestBody InputRequest<QuestionDetails> request){
        return questionDetailsService.saveRecords(request);
    }

    @PostMapping
    public ResponseEntity<OutPutResponse<List<QuestionDetails>>> getQuestionDetails(@Valid @RequestBody InputRequest<UTMForQuestions> request, BindingResult result){

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            String errorMessage = String.join(", ", errorMessages);
            logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, request, errorMessages);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OutPutResponse(new Response(new MsgInfo("400", "Failure", errorMessage))));
        }
        return ResponseEntity.ok(questionDetailsService.getQuestionDetails(request));
    }


    @PostMapping(path = "/remove")
    public OutPutResponse<String> removeRecord(@RequestBody InputRequest<QuestionDetails> request){
        return new OutPutResponse<>();
    }

    @PostMapping(path = "/update")
    public OutPutResponse<String> updateRecord(@RequestBody InputRequest<QuestionDetails> request){
        return new OutPutResponse<>();
    }

}
