package com.mli.mpro.document.mapper;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.document.models.SellerConsentDetails;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class SellerDeclarationDetailsMapper {

  private static final Logger logger = LoggerFactory.getLogger(SellerDeclarationDetailsMapper.class);
  /**
   * Mapping data from Seller declaration data to Context DataMap
   *
   * @param payload
   * @return
   */
  public Context setSellerDeclarationDataDocument(RequestPayload payload){
    Map<String, Object> dataVariables = new HashMap<>();
    SellerConsentDetails sellerConsentDetails = payload.getSellerConsentDetails();
    //FUL2-34582 update the Seller declaration content for all traditional & Term products (except CIP and sales story) for all channels
    boolean checkIsTraditionalAndNonCIPProduct = checkIsTraditionalAndNonCIPProduct(payload.getProposalDetails());
       if(sellerConsentDetails != null){
         logger.info("Set seller data for seller declaration document for sp code ={}",
             sellerConsentDetails.getSpCode());
         dataVariables.put(AppConstants.SP_NAME, sellerConsentDetails.getSellerName());
         String period;
         String periodUnit;
         period = sellerConsentDetails.getPeriod() !=0 ? String.valueOf(sellerConsentDetails.getPeriod()) : payload.getProposalDetails().getAdditionalFlags().getAgentKnowsProposerUnitType();
         periodUnit = !AppConstants.BLANK.equalsIgnoreCase(sellerConsentDetails.getPeriodUnit())? sellerConsentDetails.getPeriodUnit() : payload.getProposalDetails().getAdditionalFlags().getAgentKnowsProposerSince();
         dataVariables.put(AppConstants.PERIOD_DURATION, period +" "+ periodUnit);
         dataVariables.put(AppConstants.SELLER_NAME, sellerConsentDetails.getSellerName());
         dataVariables.put(AppConstants.SELLER_CODE, sellerConsentDetails.getSpCode());
         if(!Objects.isNull(sellerConsentDetails.getLastModifiedDate())){
           DateFormat dateFormat = new SimpleDateFormat(AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
           Calendar cal = Calendar.getInstance();
           cal.setTime(sellerConsentDetails.getLastModifiedDate());
           cal.add(Calendar.HOUR, 5);
           cal.add(Calendar.MINUTE,30);
           dataVariables.put(AppConstants.DATE_TIME, dateFormat.format(cal.getTime()));
         }
         dataVariables.put(AppConstants.PLACE, Objects.isNull(sellerConsentDetails.getAgentPlace()) || "null".equalsIgnoreCase(sellerConsentDetails.getAgentPlace()) ? "" : sellerConsentDetails.getAgentPlace());
         dataVariables.put(AppConstants.SELLER_DISCLOSURE, sellerConsentDetails.getSellerDisclosure());
         dataVariables.put("isTraditionalAndNonCIPProduct", checkIsTraditionalAndNonCIPProduct);
       }
    Context context = new Context();
    context.setVariables(dataVariables);
    return context;
  }

  
  public static boolean checkIsTraditionalAndNonCIPProduct(ProposalDetails proposalDetails) {
	  try {
      if (Objects.nonNull(proposalDetails.getProductDetails())
    		  && !proposalDetails.getProductDetails().isEmpty()
              && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())) {
    	  
          return (AppConstants.TRADITIONAL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType())
                  && !AppConstants.SELLERDECLARATION_NOTAPPLICABLE_TERMPRODUCTSSET.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()));
      }}
	  catch (Exception ex) {
          logger.error("Exception occurred at checking traditional and Non CIP prodcut at seller declaration for transactionId {} is {} ",proposalDetails.getTransactionId(),Utility.getExceptionAsString(ex));
	  }
      return false;
  }
}
