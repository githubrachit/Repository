package com.mli.mpro.configuration.service;

import java.util.List;

import com.mli.mpro.configuration.models.*;


public interface ConfigurationService {

    List<Configuration> getConfigurationByKey(String key);

    List<Configuration> getAllConfiguration();

    List<Configuration> getConfigurationByType(String type);

    MultiSelectData getMultiSelectDataByKey(String key);

    Boolean isYblTelesalesBranchCode(String branchCode);
    
    Configuration getVideoConfigurationByType(String type);

    OutputResponse getFeatureFlagData();

    FeatureFlag saveFeatureFlagData(FeatureFlagRequest featureFlagRequest);

    SupervisorDetails getSupervisorDetails(String supervisorId);
    LinkOutputResponse validateApprovalLink(String generatedLink);
    public String saveSellerSupervisorStatus(SellerSaveRequest request);
}
