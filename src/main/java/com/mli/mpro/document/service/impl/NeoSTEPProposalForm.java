package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component("NeoSTEPProposalForm")
@EnableAsync
public class NeoSTEPProposalForm extends AWPProposalForm {
    private String stepPersonalDetailsTemplate = "neo\\step\\personalDetails";
    protected String stepCoverageDetailsTemplate = "neo\\step\\coverageDetails";
    protected String stepInsuredDetailsTemplate = "neo\\step\\lifeInsuredDetails";
    protected String stepInsuredDetailsTemplateForm2 = "neo\\step\\lifeInsuredDetailsForm2";
    protected String stepMedicalDetailsTemplate = "neo\\step\\medicalDetails";
    protected String stepMedicalDetailsTemplateForm2 = "neo\\step\\medicalDetailsForm2";
    protected String stepDeclarationDetailsTemplate = "neo\\step\\pfDeclaration";
    protected String stepCkycDetailsTemplate = "neo\\step\\ckyc";
    protected String stepAnnexureDetailsTemplate = "neo\\step\\proposalFormAnnexure";
    protected String stepAnnexureDetailsTemplateForm2 = "neo\\step\\proposalFormAnnexureForm2";
    protected String stepCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String stepCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String stepCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String stepNewCovidQuestionnaireTemplate = "neo\\newCovidQuestionnaire";
    protected String stepNewCovidQuestionnaireTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String stepDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String stepDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String stepHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String stepHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String stepRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String stepRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String stepProposalFormTemplate = "neo\\step\\proposalForm";
    protected String stepjlMedicalDetailsTemplate = "neo\\step\\medicalDetailsForSSPJL";
    protected String stepjlAnnexureDetailsTemplate = "neo\\step\\proposalFormAnnexureForSSPJL";
    protected String stepjlInsuredDetailsTemplate = "neo\\step\\lifeInsuredDetailsForSSPJL";
    protected String stepjlNewCovidQuestionnaireTemplate = "neo\\step\\covidQuestionnaireNewSSPJL";
    protected String stepjlCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalseSSPJL";
    protected String stepProposalFormString = "step";


    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm(proposalDetails);
        super.generateDocument(proposalDetails);
    }

    private void setTemplateAndProposalForm(ProposalDetails proposalDetails) {
        boolean isSSPJLProduct = Utility.isSSPJLProduct(proposalDetails);
        super.personalDetailsTemplate = stepPersonalDetailsTemplate;
        super.proposalFormTemplate = stepProposalFormTemplate;
        super.coverageDetailsTemplate = stepCoverageDetailsTemplate;
        super.insuredDetailsTemplateForm2 = stepInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplateForm2 = stepMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = stepDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = stepCkycDetailsTemplate;
        super.annexureDetailsTemplateForm2 = stepAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = stepCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = stepCovidQuestionnaireAnnexureTemplateForm2;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = stepNewCovidQuestionnaireTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = stepDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = stepDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = stepHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = stepHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = stepRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = stepRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormString = stepProposalFormString;
        if(isSSPJLProduct) {
            super.medicalDetailsTemplate = stepjlMedicalDetailsTemplate;
            super.annexureDetailsTemplate = stepjlAnnexureDetailsTemplate;
            super.insuredDetailsTemplate = stepjlInsuredDetailsTemplate;
            super.newCovidQuestionnaireAnnexureTemplate = stepjlNewCovidQuestionnaireTemplate;
            super.covidQuestionnaireFalseAnnexureTemplate = stepjlCovidQuestionnaireFlaseAnnexureTemplate;
        }
        else{
            super.medicalDetailsTemplate = stepMedicalDetailsTemplate;
            super.annexureDetailsTemplate = stepAnnexureDetailsTemplate;
            super.insuredDetailsTemplate = stepInsuredDetailsTemplate;
            super.newCovidQuestionnaireAnnexureTemplate = stepNewCovidQuestionnaireTemplate;
            super.covidQuestionnaireFalseAnnexureTemplate = stepCovidQuestionnaireFlaseAnnexureTemplate;
        }
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm(proposalDetails);
        return super.getDocumentBase64(proposalDetails);
    }
}
