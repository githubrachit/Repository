package com.mli.mpro.location.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.mli.mpro.location.models.SourcingBranchCodes;
import com.mli.mpro.location.services.BranchCodeService;

@Repository
public interface BranchCodeRepository extends MongoRepository<SourcingBranchCodes,String> {
	
	@Query(value="{ status : ?0}", fields="{ branchCd : 1,_id:0 }")
	List<BranchCodeService> findBranchCdyStatus(Integer status,Pageable pageable);


}