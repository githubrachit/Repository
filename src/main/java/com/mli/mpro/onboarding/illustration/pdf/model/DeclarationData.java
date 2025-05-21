package com.mli.mpro.onboarding.illustration.pdf.model;



import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;



public class DeclarationData {

  @Sensitive(MaskType.NAME)
  private String agentName;

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  @Override
  public String toString() {
    if(Utility.isCalledFromLogs(Thread.currentThread())){
      return Utility.toString(this);
    }
    return "DeclarationData{" +
            "agentName='" + agentName + '\'' +
            '}';
  }
}
