package com.mli.mpro.tmb.utility;

import com.mli.mpro.configuration.models.Transformation;
import com.mli.mpro.configuration.repository.TransformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TMBMasterData {

    private static final Logger log = LoggerFactory.getLogger(TMBMasterData.class);

    protected static final Map<String, String> education = new HashMap<>();
    protected static final Map<String, String> occupation = new HashMap<>();
    protected static final Map<String, String> country = new HashMap<>();
    protected static final Map<String, String> maritalStatus = new HashMap<>();
    protected static final Map<String, String> accountType = new HashMap<>();

    TMBMasterData(TransformationRepository transformationRepository) {
        try {
            List<Transformation> transformations = transformationRepository.findAll();

            if (!transformations.isEmpty()) {
                for (Transformation transformation : transformations) {
                    if ("B".equals(transformation.getChannel())) {
                        switch (transformation.getFieldToTransform()) {
                            case "Education":
                                education.putAll(transformation.getTransformedMap());
                                break;
                            case "Occupation":
                                occupation.putAll(transformation.getTransformedMap());
                                break;
                            case "Country":
                                country.putAll(transformation.getTransformedMap());
                                break;
                            case "MaritalStatus":
                                maritalStatus.putAll(transformation.getTransformedMap());
                                break;
                            case "AccountType":
                                accountType.putAll(transformation.getTransformedMap());
                                break;
                            default:
                                log.info("Field {} not recognized", transformation.getFieldToTransform());
                        }
                    }
                }
            } else {
                log.info("No transformations received from DB");
            }
        } catch (Exception e) {
            log.error("Exception occurred while fetching data: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getEducation(String key) {
        return education.get(key);
    }

    public static String getOccupation(String key) {
        return occupation.get(key);
    }

    public static String getCountry(String key) {
        return country.get(key);
    }

    public static String getMaritalStatus(String key) {
        return maritalStatus.get(key);
    }

    public static String getAccountType(String key) {
        return accountType.get(key);
    }

}
