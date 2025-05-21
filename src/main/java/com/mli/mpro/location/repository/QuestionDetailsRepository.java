package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.journeyQuestions.QuestionDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionDetailsRepository extends MongoRepository<QuestionDetails, String> {
    List<QuestionDetails> findAll();

    List<QuestionDetails> findByChannelsAndProducts(String channel,String product);

}
