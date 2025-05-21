package com.mli.mpro.configuration.repository;

import com.mli.mpro.configuration.models.LEProductList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LEProductListRepository extends MongoRepository<LEProductList , String> {
}
