package com.mli.mpro.utils;

import com.mli.mpro.neo.models.attachment.GetAttachmentApiRequest;
import com.mli.mpro.proposal.models.InputRequest;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Chandra on 29/07/20
 */
public class MDCHelper {

    public static final String REQUEST_ID = "requestId";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String ERROR = "Error in setting requestId, transactionId to context  ";

    private MDCHelper(){
        // Added private constructor as it only contains static member
        // to resolve SonarQube major issue.
    }
    private static final Logger logger = LoggerFactory.getLogger(MDCHelper.class);

    public static void setLogVariable(InputRequest inputRequest){
      try {
          MDC.put(REQUEST_ID, Optional.ofNullable(inputRequest)
                  .map(r -> r.getRequest())
                  .map(f -> f.getMetadata())
                  .map(t -> t.getRequestId())
                  .orElse(" "));
          MDC.put(TRANSACTION_ID, Optional.ofNullable(inputRequest)
                  .map(r -> r.getRequest())
                  .map(f -> f.getRequestData())
                  .map(s -> s.getRequestPayload())
                  .map(t -> t.getProposalDetails())
                  .map(u -> u.getTransactionId())
                  .orElse(0l));
      }
      catch (Exception ex) {
          logger.error(ERROR, ex);
      }
    }
    public static void setLogVariable(com.mli.mpro.document.models.InputRequest inputRequest){
        try {
            MDC.put(REQUEST_ID, Optional.ofNullable(inputRequest)
                    .map(r -> r.getRequest())
                    .map(f -> f.getMetadata())
                    .map(t -> t.getRequestId())
                    .orElse(" "));

        }
        catch (Exception ex) {
            logger.error("Error in setting requestId to context  ", ex);
        }
    }
    public static void setLogVariable(GetAttachmentApiRequest inputRequest){
        try {
            MDC.put(TRANSACTION_ID, Optional.ofNullable(inputRequest)
                    .map(r -> r.getRequest())
                    .map(s -> s.getPayload())
                    .map(t -> t.getProposalDetails())
                    .map(u -> u.getTransactionId())
                    .orElse(0l));
        }
        catch (Exception ex) {
            logger.error("Error in setting transactionId to context  ", ex);
        }
    }
    public static void setLogVariable(com.mli.mpro.common.models.InputRequest inputRequest, boolean setTransactionId){
        try {

                MDC.put(REQUEST_ID, Optional.ofNullable(inputRequest)
                        .map(r -> r.getRequest())
                        .map(f -> f.getMetadata())
                        .map(t -> t.getRequestId())
                        .orElse(" "));
                if (setTransactionId) {
                    MDC.put(TRANSACTION_ID, Optional.ofNullable(inputRequest)
                            .map(r -> r.getRequest())
                            .map(f -> f.getRequestData())
                            .map(s -> s.getRequestPayload())
                            .map(t -> t.getProposalDetails())
                            .map(u -> u.getTransactionId())
                            .orElse(0l));
                }

        }
        catch (Exception ex) {
            logger.error(ERROR, ex);
        }
    }
    public static void setLogVariable(com.mli.mpro.productRestriction.models.InputRequest inputRequest, boolean setTransactionId){
        try {

                MDC.put(REQUEST_ID, Optional.ofNullable(inputRequest)
                        .map(r -> r.getRequest())
                        .map(f -> f.getMetadata())
                        .map(t -> t.getRequestId())
                        .orElse(" "));
                if (setTransactionId) {
                    MDC.put(TRANSACTION_ID, Optional.ofNullable(inputRequest)
                            .map(r -> r.getRequest())
                            .map(f -> f.getRequestData())
                            .map(s -> s.getRequestPayload())
                            .map(t -> t.getProductRestrictionPayload())
                            .map(u -> u.getTransactionId())
                            .orElse(0l));
                }

        }
        catch (Exception ex) {
            logger.error(ERROR, ex);
        }
    }
}
