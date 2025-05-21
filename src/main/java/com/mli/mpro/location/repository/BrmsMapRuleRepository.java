package com.mli.mpro.location.repository;

import com.mli.mpro.location.training.model.BrmsMapRule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrmsMapRuleRepository extends MongoRepository<BrmsMapRule, String> {
    BrmsMapRule findByChannelAndRole(String channel, String role);
}
