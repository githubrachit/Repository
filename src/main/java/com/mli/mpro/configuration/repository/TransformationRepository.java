package com.mli.mpro.configuration.repository;

import com.mli.mpro.configuration.models.Transformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransformationRepository extends MongoRepository<Transformation,String> {
    List<Transformation> findByChannel(String channel);
}
