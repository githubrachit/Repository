package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SellerConsentQuestionnaire {
  private String questionId;
  @Sensitive(MaskType.MASK_ALL)
  private String additionalInformation;
  private String answer;
  public String getQuestionId() {
    return questionId;
  }
  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }
  public String getAdditionalInformation() {
    return additionalInformation;
  }
  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }
  public String getAnswer() {
    return answer;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }
  @Override
  public String toString() {
    if(Utility.isCalledFromLogs(Thread.currentThread())){
      return Utility.toString(this);
    }
    return "SellerConsentQuestionnaire{" +
        "  questionId='" + questionId + '\'' +
        ", question='" + additionalInformation + '\'' +
        ", answer='" + answer + '\'' +
        '}';
  }
}
