package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author manish on 22/05/20
 */
@Component("neoSWPProposalForm")
@EnableAsync
public class NeoSWPProposalForm extends AWPProposalForm {

    protected String swpPersonalDetailsTemplate = "neo\\swp\\personalDetails";
    protected String swpLumpsumPersonalDetailsTemplate = "neo\\swp\\lumpsumPersonalDetails";
    protected String swpCoverageDetailsTemplate = "neo\\swp\\coverageDetails";
    protected String swpInsuredDetailsTemplate = "neo\\swp\\lifeInsuredDetails";
    protected String swpInsuredDetailsTemplateForm2 = "neo\\swp\\lifeInsuredDetailsForm2";
    protected String swpMedicalDetailsTemplate = "neo\\swp\\medicalDetails";
    protected String swpMedicalDetailsTemplateForm2 = "neo\\swp\\medicalDetailsForm2";
    protected String swpDeclarationDetailsTemplate = "neo\\swp\\pfDeclaration";
    protected String swpCkycDetailsTemplate = "neo\\swp\\ckyc";
    protected String swpAnnexureDetailsTemplate = "neo\\swp\\proposalFormAnnexure";
    protected String swpAnnexureDetailsTemplateForm2 = "neo\\swp\\proposalFormAnnexureForm2";
    protected String swpCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String swpCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String swpCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String swpNewCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String swpNewCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String swpDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String swpDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String swpHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String swpHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String swpRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String swpRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String swpProposalFormTemplate = "neo\\swp\\proposalForm";
    protected String swpLumpsumProposalFormTemplate = "neo\\swp\\lumpsumProposalForm";
    protected String swpProposalFormString = "SWP";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm(proposalDetails);
        super.generateDocument(proposalDetails);
    }

    private void setTemplateAndProposalForm(ProposalDetails proposalDetails) {
        boolean isSWPLumpsum = Utility.checkIsSWPLumpsum(proposalDetails);
        if (isSWPLumpsum) {
            super.personalDetailsTemplate = swpLumpsumPersonalDetailsTemplate;
            super.proposalFormTemplate = swpLumpsumProposalFormTemplate;
        } else {
            super.personalDetailsTemplate = swpPersonalDetailsTemplate;
            super.proposalFormTemplate = swpProposalFormTemplate;
        }
        super.coverageDetailsTemplate = swpCoverageDetailsTemplate;
        super.insuredDetailsTemplate = swpInsuredDetailsTemplate;
        super.insuredDetailsTemplateForm2 = swpInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplate = swpMedicalDetailsTemplate;
        super.medicalDetailsTemplateForm2 = swpMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = swpDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = swpCkycDetailsTemplate;
        super.annexureDetailsTemplate = swpAnnexureDetailsTemplate;
        super.annexureDetailsTemplateForm2 = swpAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = swpCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = swpCovidQuestionnaireAnnexureTemplateForm2;
        super.covidQuestionnaireFalseAnnexureTemplate = swpCovidQuestionnaireFlaseAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplate = swpNewCovidQuestionnaireAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = swpNewCovidQuestionnaireAnnexureTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = swpDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = swpDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = swpHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = swpHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = swpRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = swpRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormTemplate = swpProposalFormTemplate;
        super.proposalFormString = swpProposalFormString;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm(proposalDetails);
        return super.getDocumentBase64(proposalDetails);
    }
}
