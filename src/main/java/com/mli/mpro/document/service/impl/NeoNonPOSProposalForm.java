package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component("neoNonPOSProposalForm")
@EnableAsync
public class NeoNonPOSProposalForm extends AWPProposalForm {

    protected String nonPosPersonalDetailsTemplate = "neo\\nonpos\\personalDetails";
    protected String nonPosCoverageDetailsTemplate = "neo\\nonpos\\coverageDetails";
    protected String nonPosInsuredDetailsTemplate = "neo\\nonpos\\lifeInsuredDetails";
    protected String nonPosInsuredDetailsTemplateForm2 = "neo\\nonpos\\lifeInsuredDetailsForm2";
    protected String nonPosMedicalDetailsTemplate = "neo\\nonpos\\medicalDetailsForm1";
    protected String nonPosMedicalDetailsTemplateForm2 = "neo\\nonpos\\medicalDetailsForm2";
    protected String nonPosDeclarationDetailsTemplate = "neo\\nonpos\\pfDeclaration";
    protected String nonPosCkycDetailsTemplate = "neo\\ckyc";
    protected String nonPosAnnexureDetailsTemplate = "neo\\nonpos\\proposalFormAnnexure";
    protected String nonPosAnnexureDetailsTemplateForm2 = "neo\\nonpos\\proposalFormAnnexureForm2";
    protected String nonPosCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String nonPosCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String nonPosCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String nonPosNewCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String nonposNewCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String nonPosDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String nonPosDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String nonPosHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String nonPosHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String nonPosRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";

    protected String nonPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 =  "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String nonPosProposalFormTemplate = "neo\\nonpos\\proposalForm";
    protected String nonPosProposalFormString = "POS";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm();
        super.generateDocument(proposalDetails);
    }
    private void setTemplateAndProposalForm() {
        super.personalDetailsTemplate = nonPosPersonalDetailsTemplate;
        super.proposalFormTemplate = nonPosProposalFormTemplate;
        super.coverageDetailsTemplate = nonPosCoverageDetailsTemplate;
        super.insuredDetailsTemplate = nonPosInsuredDetailsTemplate;
        super.insuredDetailsTemplateForm2 = nonPosInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplate = nonPosMedicalDetailsTemplate;
        super.medicalDetailsTemplateForm2 = nonPosMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = nonPosDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = nonPosCkycDetailsTemplate;
        super.annexureDetailsTemplate = nonPosAnnexureDetailsTemplate;
        super.annexureDetailsTemplateForm2 = nonPosAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = nonPosCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2=nonPosCovidQuestionnaireAnnexureTemplateForm2;
        super.covidQuestionnaireFalseAnnexureTemplate = nonPosCovidQuestionnaireFlaseAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplate = nonPosNewCovidQuestionnaireAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = nonposNewCovidQuestionnaireAnnexureTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate =nonPosDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2=nonPosDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate =nonPosHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2=nonPosHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate =nonPosRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2=nonPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormString = nonPosProposalFormString;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm();
        return super.getDocumentBase64(proposalDetails);
    }
}
