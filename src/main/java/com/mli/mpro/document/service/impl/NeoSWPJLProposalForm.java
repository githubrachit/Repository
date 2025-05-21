package com.mli.mpro.document.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author prajjwal on 02/06/21
 */
@Component
@EnableAsync
public class NeoSWPJLProposalForm extends NeoSWPProposalForm {

  protected String swpjlPersonalDetailsTemplate = "neo\\swpjl\\personalDetails";
  protected String swpLjlumpsumPersonalDetailsTemplate = "neo\\swpjl\\lumpsumPersonalDetails";
  protected String swpjlCoverageDetailsTemplate = "neo\\swpjl\\coverageDetails";
  protected String swpjlInsuredDetailsTemplate = "neo\\swpjl\\lifeInsuredDetails";
  protected String swpjlMedicalDetailsTemplate = "neo\\swpjl\\medicalDetails";
  protected String swpjlDeclarationDetailsTemplate = "neo\\swpjl\\pfDeclaration";
  protected String swpjlCkycDetailsTemplate = "neo\\swpjl\\ckyc";
  protected String swpjlAnnexureDetailsTemplate = "neo\\swpjl\\proposalFormAnnexure";
  protected String swpjlCovidQuestionnaireAnnexureTemplate = "neo\\swpjl\\covidQuestionnaire";
  protected String swpjlCovidQuestionnaireFlaseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
  protected String swpjlNewCovidQuestionnaireAnnexureTemplate = "neo\\swpjl\\newCovidQuestionnaire";
  protected String swpjlDiabeticQuestionnaireAnnexureTemplate = "neo\\swpjl\\diabeticQuestionnaire";
  protected String swpjlHighBloodPressureQuestionnaireAnnexureTemplate = "neo\\swpjl\\highBloodPressureQuestionnaire";
  protected String swpjlRespiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
  protected String swpjlProposalFormTemplate = "neo\\swpjl\\proposalForm";
  protected String swpjlLumpsumProposalFormTemplate = "neo\\swpjl\\lumpsumProposalForm";
  protected String swpjlProposalFormString = "SWPJL";

  public NeoSWPJLProposalForm() {
    super.swpPersonalDetailsTemplate = swpjlPersonalDetailsTemplate;
    super.swpLumpsumPersonalDetailsTemplate = swpLjlumpsumPersonalDetailsTemplate;
    super.swpCoverageDetailsTemplate = swpjlCoverageDetailsTemplate;
    super.swpInsuredDetailsTemplate = swpjlInsuredDetailsTemplate;
    super.swpMedicalDetailsTemplate = swpjlMedicalDetailsTemplate;
    super.swpDeclarationDetailsTemplate = swpjlDeclarationDetailsTemplate;
    super.swpCkycDetailsTemplate = swpjlCkycDetailsTemplate;
    super.swpAnnexureDetailsTemplate = swpjlAnnexureDetailsTemplate;
    super.swpCovidQuestionnaireAnnexureTemplate = swpjlCovidQuestionnaireAnnexureTemplate;
    super.swpCovidQuestionnaireFlaseAnnexureTemplate = swpjlCovidQuestionnaireFlaseAnnexureTemplate;
    super.swpNewCovidQuestionnaireAnnexureTemplate = swpjlNewCovidQuestionnaireAnnexureTemplate;
    super.swpDiabeticQuestionnaireAnnexureTemplate = swpjlDiabeticQuestionnaireAnnexureTemplate;
    super.swpHighBloodPressureQuestionnaireAnnexureTemplate = swpjlHighBloodPressureQuestionnaireAnnexureTemplate;
    super.swpRespiratoryDisorderQuestionnaireAnnexureTemplate = swpjlRespiratoryDisorderQuestionnaireAnnexureTemplate;
    super.swpProposalFormTemplate = swpjlProposalFormTemplate;
    super.swpLumpsumProposalFormTemplate = swpjlLumpsumProposalFormTemplate;
    super.swpProposalFormString = swpjlProposalFormString;

  }
}
