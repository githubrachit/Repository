package com.mli.mpro.configuration;

import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.location.models.DisableProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Configuration
public class DisableProductsData {
    private static final Logger logger = LoggerFactory.getLogger(DisableProductsData.class);

    public static Map<String,List<String>> posDisableProducts = new HashMap<>();
    public static Map<String,List<String>> nonPosDisableProducts = new HashMap<>();
    public static Map<String,String> channelRegex = new HashMap<>();

    @Autowired
    public DisableProductsData(ConfigurationRepository configurationRepository){

        try{
            logger.info("Loading disable products");
            com.mli.mpro.configuration.models.Configuration configuration = configurationRepository.findByType("disableProducts");
            channelRegex = configuration.getChannelRegex();
            for(DisableProduct data: configuration.getPosDisableProductList()){
                posDisableProducts.put(data.getChannelName(),data.getProducts());
            }
            for(DisableProduct data: configuration.getNonPosDisableProductList()){
                nonPosDisableProducts.put(data.getChannelName(),data.getProducts());
            }
        } catch (Exception e){
            logger.error("Exception occured during loading disable products");
        }
    }
}