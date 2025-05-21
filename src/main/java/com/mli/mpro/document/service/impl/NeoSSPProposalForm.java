package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
/**
 * @author manish on 22/05/20
 */
@Component("neoSSPProposalForm")
@EnableAsync
public class NeoSSPProposalForm extends AWPProposalForm {
    private String sspPersonalDetailsTemplate = "neo\\ssp\\personalDetails";
    protected String sspCoverageDetailsTemplate = "neo\\ssp\\coverageDetails";
    protected String sspInsuredDetailsTemplate = "neo\\ssp\\lifeInsuredDetails";
    protected String sspInsuredDetailsTemplateForm2 = "neo\\ssp\\lifeInsuredDetailsForm2";
    protected String sspMedicalDetailsTemplate = "neo\\ssp\\medicalDetails";
    protected String sspMedicalDetailsTemplateForm2 = "neo\\ssp\\medicalDetailsForm2";
    protected String sspDeclarationDetailsTemplate = "neo\\ssp\\pfDeclaration";
    protected String sspCkycDetailsTemplate = "neo\\ssp\\ckyc";
    protected String sspAnnexureDetailsTemplate = "neo\\ssp\\proposalFormAnnexure";
    protected String sspAnnexureDetailsTemplateForm2 = "neo\\ssp\\proposalFormAnnexureForm2";
    protected String sspCovidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String sspCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String sspCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String sspNewCovidQuestionnaireTemplate = "neo\\newCovidQuestionnaire";
    protected String sspNewCovidQuestionnaireTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String sspDiabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String sspDiabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String sspHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String sspHighBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String sspRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String sspRespiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String sspProposalFormTemplate = "neo\\ssp\\proposalForm";
    protected String sspProposalFormString = "SSP";
    protected String sspjlMedicalDetailsTemplate = "neo\\ssp\\medicalDetailsForSSPJL";
    protected String sspjlAnnexureDetailsTemplate = "neo\\ssp\\proposalFormAnnexureForSSPJL";
    protected String sspjlInsuredDetailsTemplate = "neo\\ssp\\lifeInsuredDetailsForSSPJL";
    protected String sspjlNewCovidQuestionnaireTemplate = "neo\\ssp\\covidQuestionnaireNewSSPJL";
    protected String sspjlCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalseSSPJL";


    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        setTemplateAndProposalForm(proposalDetails);
        super.generateDocument(proposalDetails);
    }

    private void setTemplateAndProposalForm(ProposalDetails proposalDetails) {
        boolean isSSPJLProduct = Utility.isSSPJLProduct(proposalDetails);
        super.personalDetailsTemplate = sspPersonalDetailsTemplate;
        super.proposalFormTemplate = sspProposalFormTemplate;
        super.coverageDetailsTemplate = sspCoverageDetailsTemplate;
        super.insuredDetailsTemplateForm2 = sspInsuredDetailsTemplateForm2;
        super.medicalDetailsTemplateForm2 = sspMedicalDetailsTemplateForm2;
        super.declarationDetailsTemplate = sspDeclarationDetailsTemplate;
        super.ckycDetailsTemplate = sspCkycDetailsTemplate;
        super.annexureDetailsTemplateForm2 = sspAnnexureDetailsTemplateForm2;
        super.covidQuestionnaireAnnexureTemplate = sspCovidQuestionnaireAnnexureTemplate;
        super.covidQuestionnaireAnnexureTemplateForm2 = sspCovidQuestionnaireAnnexureTemplateForm2;
        super.newCovidQuestionnaireAnnexureTemplateForm2 = sspNewCovidQuestionnaireTemplateForm2;
        super.diabeticQuestionnaireAnnexureTemplate = sspDiabeticQuestionnaireAnnexureTemplate;
        super.diabeticQuestionnaireAnnexureTemplateForm2 = sspDiabeticQuestionnaireAnnexureTemplateForm2;
        super.highBloodPressureQuestionnaireAnnexureTemplate = sspHighBloodPressureQuestionnaireAnnexureTemplate;
        super.highBloodPressureQuestionnaireAnnexureTemplateForm2 = sspHighBloodPressureQuestionnaireAnnexureTemplateForm2;
        super.respiratoryDisorderQuestionnaireAnnexureTemplate = sspRespiratoryDisorderQuestionnaireAnnexureTemplate;
        super.respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = sspRespiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        super.proposalFormString = sspProposalFormString;
        if(isSSPJLProduct){
            super.medicalDetailsTemplate = sspjlMedicalDetailsTemplate;
            super.annexureDetailsTemplate = sspjlAnnexureDetailsTemplate;
            super.insuredDetailsTemplate = sspjlInsuredDetailsTemplate;
            super.newCovidQuestionnaireAnnexureTemplate = sspjlNewCovidQuestionnaireTemplate;
            super.covidQuestionnaireFalseAnnexureTemplate = sspjlCovidQuestionnaireFlaseAnnexureTemplate;
        }else {
            super.medicalDetailsTemplate = sspMedicalDetailsTemplate;
            super.annexureDetailsTemplate = sspAnnexureDetailsTemplate;
            super.insuredDetailsTemplate = sspInsuredDetailsTemplate;
            super.newCovidQuestionnaireAnnexureTemplate = sspNewCovidQuestionnaireTemplate;
            super.covidQuestionnaireFalseAnnexureTemplate = sspCovidQuestionnaireFlaseAnnexureTemplate;
        }
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        setTemplateAndProposalForm(proposalDetails);
        return super.getDocumentBase64(proposalDetails);
    }
}
