package com.mli.mpro.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mli.mpro.location.models.AllFund;
import com.mli.mpro.location.models.RecommendedFunds;
import com.mli.mpro.location.repository.AllFundRepository;
import com.mli.mpro.location.repository.RecommendedFundRepository;

@Configuration
public class UlipFundsData {
	private static final Logger logger = LoggerFactory.getLogger(UlipFundsData.class);

    private AllFundRepository allFundRepository;
    private RecommendedFundRepository recommendedFundRepository;

    public static List<AllFund> allFunds = new ArrayList<>();
    public static List<RecommendedFunds> recommendedFunds = new ArrayList<>();
    

    @Autowired
    public UlipFundsData(AllFundRepository allFundRepository, RecommendedFundRepository recommendedFundRepository){
        this.allFundRepository = allFundRepository;
        this.recommendedFundRepository = recommendedFundRepository;
        logger.info("loading funds at startup");
        allFunds = allFundRepository.findAll();
        recommendedFunds = recommendedFundRepository.findAll();
    }
}
