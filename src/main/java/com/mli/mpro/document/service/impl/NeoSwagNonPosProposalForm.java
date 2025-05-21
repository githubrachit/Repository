package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component("neoSwagNonPosProposalForm")
@EnableAsync
public class NeoSwagNonPosProposalForm extends AWPProposalForm {
    protected String swagNonPosPersonalDetailsTemplate = "neo\\swagNonPos\\personalDetails";
    protected String swagNonPosCoverageDetailsTemplate = "neo\\swagNonPos\\coverageDetails";
    protected String swagNonPosInsuredDetailsTemplate = "neo\\swagNonPos\\lifeInsuredDetails";
    protected String swagNonPosInsuredDetailsTemplateForm2 = "neo\\swagNonPos\\lifeInsuredDetailsForm2";
    protected String swagNonPosMedicalDetailsTemplate = "neo\\swagNonPos\\medicalDetailsForm1";
    protected String swagNonPosMedicalDetailsTemplateForm2 = "neo\\swagNonPos\\medicalDetailsForm2";
    protected String swagNonPosDeclarationDetailsTemplate = "neo\\swagNonPos\\pfDeclaration";
    protected String swagNonPosCkycDetailsTemplate = "neo\\ckyc";
    protected String swagNonPosAnnexureDetailsTemplate = "neo\\swagNonPos\\proposalFormAnnexure";
    protected String swagNonPosAnnexureDetailsTemplateForm2 = "neo\\swagNonPos\\proposalFormAnnexureForm2";
    protected String swagNonPosCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String swagNonPosCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String swagNonPosCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String swagNonPosNewCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String swagNonPosNewCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String swagNonPosDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String swagNonPosDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String swagNonPosHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String swagNonPosHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String swagNonPosRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";

    protected String swagNonPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 =  "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String swagNonPosProposalFormTemplate = "neo\\swagNonPos\\proposalForm";
    protected String swagNonPosProposalFormString = "Swag_Non_POS";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm();
        super.generateDocument(proposalDetails);
    }
    private void setTemplateAndProposalForm() {
        super.personalDetailsTemplate = swagNonPosPersonalDetailsTemplate;
        super.proposalFormTemplate = swagNonPosProposalFormTemplate;
        super.coverageDetailsTemplate = swagNonPosCoverageDetailsTemplate;
        super.insuredDetailsTemplate = swagNonPosInsuredDetailsTemplate;
        super.insuredDetailsTemplateForm2 = swagNonPosInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplate = swagNonPosMedicalDetailsTemplate;
        super.medicalDetailsTemplateForm2 = swagNonPosMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = swagNonPosDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = swagNonPosCkycDetailsTemplate;
        super.annexureDetailsTemplate = swagNonPosAnnexureDetailsTemplate;
        super.annexureDetailsTemplateForm2 = swagNonPosAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = swagNonPosCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = swagNonPosCovidQuestionnaireAnnexureTemplateForm2;
        super.covidQuestionnaireFalseAnnexureTemplate = swagNonPosCovidQuestionnaireFlaseAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplate = swagNonPosNewCovidQuestionnaireAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = swagNonPosNewCovidQuestionnaireAnnexureTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = swagNonPosDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = swagNonPosDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = swagNonPosHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = swagNonPosHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = swagNonPosRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = swagNonPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormString = swagNonPosProposalFormString;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm();
        return super.getDocumentBase64(proposalDetails);
    }
}
