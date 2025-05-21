package com.mli.mpro.auditservice;

import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.common.exception.UserHandledException;

public interface AuditService {

	public void saveAuditTransactionDetails(AuditingDetails auditDetails) throws UserHandledException;

	public AuditingDetails getAuditDetails(String auditId, String serviceName, String requestId);

	public void saveAuditTransactionDetailsForAgentSelf(AuditingDetails auditDetails,String requestId) throws UserHandledException;

	public void logAuditDetails(long transactionId, Object request, Object response, String serviceName, String agentId) throws UserHandledException;

}
