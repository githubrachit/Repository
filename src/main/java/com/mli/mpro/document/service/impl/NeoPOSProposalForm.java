package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
/**
 * @author Akash on 10/05/22
 */
@Component("neoPosProposalForm")
@EnableAsync
public class NeoPOSProposalForm extends AWPProposalForm {
    protected String posPersonalDetailsTemplate = "neo\\pos\\personalDetails";
    protected String posCoverageDetailsTemplate = "neo\\pos\\coverageDetails";
    protected String posInsuredDetailsTemplate = "neo\\pos\\lifeInsuredDetails";
    protected String posInsuredDetailsTemplateForm2 = "neo\\pos\\lifeInsuredDetailsForm2";
    protected String posMedicalDetailsTemplate = "neo\\pos\\medicalDetailsForm1";
    protected String posMedicalDetailsTemplateForm2 = "neo\\pos\\medicalDetailsForm2";
    protected String posMedicalDetailsTemplateAxisD2C = "neo\\pos\\medicalDetailsAxisD2C";
    protected String posDeclarationDetailsTemplate = "neo\\pos\\pfDeclaration";
    protected String posCkycDetailsTemplate = "neo\\pos\\ckyc";
    protected String posAnnexureDetailsTemplate = "neo\\pos\\proposalFormAnnexure";
    protected String posAnnexureDetailsTemplateForm2 = "neo\\pos\\proposalFormAnnexureForm2";
    protected String posCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String posCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String posCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String posNewCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String posNewCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String posDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String posDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String posHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String posHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String posRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String posRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String posProposalFormTemplate = "neo\\pos\\proposalForm";
    protected String posProposalFormString = "POS";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm();
        super.generateDocument(proposalDetails);
    }
    private void setTemplateAndProposalForm() {
        super.personalDetailsTemplate = posPersonalDetailsTemplate;
        super.proposalFormTemplate = posProposalFormTemplate;
        super.coverageDetailsTemplate = posCoverageDetailsTemplate;
        super.insuredDetailsTemplate = posInsuredDetailsTemplate;
        super.insuredDetailsTemplateForm2 = posInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplate = posMedicalDetailsTemplate;
        super.medicalDetailsTemplateForm2 = posMedicalDetailsTemplateForm2;
        super.medicalDetailsTemplateAxisD2C = posMedicalDetailsTemplateAxisD2C;
        super.declarationDetailsTemplate = posDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = posCkycDetailsTemplate;
        super.annexureDetailsTemplate = posAnnexureDetailsTemplate;
        super.annexureDetailsTemplateForm2 = posAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = posCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = posCovidQuestionnaireAnnexureTemplateForm2;
        super.covidQuestionnaireFalseAnnexureTemplate = posCovidQuestionnaireFlaseAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplate = posNewCovidQuestionnaireAnnexureTemplate;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = posNewCovidQuestionnaireAnnexureTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = posDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = posDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = posHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = posHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = posRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = posRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormString = posProposalFormString;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm();
        return super.getDocumentBase64(proposalDetails);
    }
}
