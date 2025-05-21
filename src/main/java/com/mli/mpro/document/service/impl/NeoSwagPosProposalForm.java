package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component("neoSwagPosProposalForm")
@EnableAsync
public class NeoSwagPosProposalForm extends AWPProposalForm{
    protected String swagPosPersonalDetailsTemplate = "neo\\swagPos\\personalDetails";
    protected String swagPosCoverageDetailsTemplate = "neo\\swagPos\\coverageDetails";
    protected String swagPosInsuredDetailsTemplate = "neo\\swagPos\\lifeInsuredDetails";
    protected String swagPosInsuredDetailsTemplateForm2 = "neo\\swagPos\\lifeInsuredDetailsForm2";
    protected String swagPosMedicalDetailsTemplate = "neo\\swagPos\\medicalDetailsForm1";
    protected String swagPosMedicalDetailsTemplateForm2 = "neo\\swagPos\\medicalDetailsForm2";
    protected String swagPosDeclarationDetailsTemplate = "neo\\swagPos\\pfDeclaration";
    protected String swagPosCkycDetailsTemplate = "neo\\swagPos\\ckyc";
    protected String swagPosAnnexureDetailsTemplate = "neo\\swagPos\\proposalFormAnnexure";
    protected String swagPosAnnexureDetailsTemplateForm2 = "neo\\swagPos\\proposalFormAnnexureForm2";
    protected String swagPosCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String swagPosCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String swagPosCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String swagPosNewCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String swagPosNewCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String swagPosDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String swagPosDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String swagPosHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String swagPosHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String swagPosRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String swagPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String swagPosProposalFormTemplate = "neo\\swagPos\\proposalForm";
    protected String swagPosProposalFormString = "Swag_Pos";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm();
        super.generateDocument(proposalDetails);
    }

    private void setTemplateAndProposalForm() {
        super.personalDetailsTemplate = swagPosPersonalDetailsTemplate;
        super.coverageDetailsTemplate = swagPosCoverageDetailsTemplate;
        super.insuredDetailsTemplate = swagPosInsuredDetailsTemplate;
        super.insuredDetailsTemplateForm2 = swagPosInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplate = swagPosMedicalDetailsTemplate;
        super.medicalDetailsTemplateForm2 = swagPosMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = swagPosDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = swagPosCkycDetailsTemplate;
        super.annexureDetailsTemplate = swagPosAnnexureDetailsTemplate;
        super.annexureDetailsTemplateForm2 = swagPosAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = swagPosCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = swagPosCovidQuestionnaireAnnexureTemplateForm2;
        super.covidQuestionnaireFalseAnnexureTemplate = swagPosCovidQuestionnaireFlaseAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplate = swagPosNewCovidQuestionnaireAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = swagPosNewCovidQuestionnaireAnnexureTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = swagPosDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = swagPosDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = swagPosHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = swagPosHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = swagPosRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = swagPosRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormTemplate = swagPosProposalFormTemplate;
        super.proposalFormString = swagPosProposalFormString;

    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm();
        return super.getDocumentBase64(proposalDetails);
    }
}
