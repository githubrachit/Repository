package com.mli.mpro.onboarding.illustration.pdf.model;

import java.util.Arrays;



public class IllustrationPdfRequest {

  private String proposalNo;

  private Object illustrationOutput;

  private DeclarationData declarationData;

  public String getProposalNo() {
    return proposalNo;
  }

  public void setProposalNo(String proposalNo) {
    this.proposalNo = proposalNo;
  }

  public Object getIllustrationOutput() {
    return illustrationOutput;
  }

  public void setIllustrationOutput(Object illustrationOutput) {
    this.illustrationOutput = illustrationOutput;
  }

  public DeclarationData getDeclarationData() {
    return declarationData;
  }

  public void setDeclarationData(DeclarationData declarationData) {
    this.declarationData = declarationData;
  }

  @Override
  public String toString() {
    return "IllustrationPdfRequest{" +
            "proposalNo='" + proposalNo + '\'' +
            ", illustrationOutput=" + illustrationOutput +
            ", declarationData=" + declarationData +
            '}';
  }
}
