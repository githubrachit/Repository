package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component("neoSTPProposalForm")
@EnableAsync
public class NeoSTPProposalForm extends AWPProposalForm{
    protected String stppPersonalDetailsTemplate = "neo\\stpp\\personalDetails";
    protected String stppCoverageDetailsTemplate = "neo\\stpp\\coverageDetails";
    protected String stppInsuredDetailsTemplate = "neo\\stpp\\lifeInsuredDetails";
    protected String stppMedicalDetailsTemplate = "neo\\stpp\\medicalDetails";
    protected String stppDeclarationDetailsTemplate = "neo\\stpp\\pfDeclaration";
    protected String stppProposalFormTemplate = "neo\\stpp\\proposalForm";
    protected String stppCkycDetailsTemplate = "neo\\stpp\\ckyc";
    protected String stppAnnexureDetailsTemplate = "neo\\stpp\\proposalFormAnnexure";
    protected String stppProposalFormString = "stpp";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm(proposalDetails);
        super.generateDocument(proposalDetails);
    }

    private void setTemplateAndProposalForm(ProposalDetails proposalDetails) {
        super.personalDetailsTemplate = stppPersonalDetailsTemplate;
        super.coverageDetailsTemplate = stppCoverageDetailsTemplate;
        super.insuredDetailsTemplate = stppInsuredDetailsTemplate;
        super.medicalDetailsTemplate = stppMedicalDetailsTemplate;
        super.declarationDetailsTemplate = stppDeclarationDetailsTemplate;
        super.proposalFormTemplate = stppProposalFormTemplate;
        super.proposalFormString = stppProposalFormString;
        super.ckycDetailsTemplate = stppCkycDetailsTemplate;
        super.annexureDetailsTemplate = stppAnnexureDetailsTemplate;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm(proposalDetails);
        return super.getDocumentBase64(proposalDetails);
    }
}
