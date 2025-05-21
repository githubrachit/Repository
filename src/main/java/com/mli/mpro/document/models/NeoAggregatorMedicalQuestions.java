package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

public class NeoAggregatorMedicalQuestions extends MedicalQuestions {

  private String question1CChoice;
  private String question1DChoice;
  private String question4EChoice;
  private String question4FChoice;
  private String question4GChoice;
  private String question5DChoice;
  private String question7BChoice;
  private String question7CChoice;
  private String question7DChoice;
  private String question10BChoice;
  private String question10CChoice;
  private String question11AChoice;

  public String getQuestion1CChoice() {
    return question1CChoice;
  }

  public void setQuestion1CChoice(String question1CChoice) {
    this.question1CChoice = question1CChoice;
  }

  public String getQuestion1DChoice() {
    return question1DChoice;
  }

  public void setQuestion1DChoice(String question1DChoice) {
    this.question1DChoice = question1DChoice;
  }

  public String getQuestion4EChoice() {
    return question4EChoice;
  }

  public void setQuestion4EChoice(String question4EChoice) {
    this.question4EChoice = question4EChoice;
  }

  public String getQuestion4FChoice() {
    return question4FChoice;
  }

  public void setQuestion4FChoice(String question4FChoice) {
    this.question4FChoice = question4FChoice;
  }

  public String getQuestion4GChoice() {
    return question4GChoice;
  }

  public void setQuestion4GChoice(String question4GChoice) {
    this.question4GChoice = question4GChoice;
  }

  public String getQuestion5DChoice() {
    return question5DChoice;
  }

  public void setQuestion5DChoice(String question5DChoice) {
    this.question5DChoice = question5DChoice;
  }

  public String getQuestion7BChoice() {
    return question7BChoice;
  }

  public void setQuestion7BChoice(String question7BChoice) {
    this.question7BChoice = question7BChoice;
  }

  public String getQuestion7CChoice() {
    return question7CChoice;
  }

  public void setQuestion7CChoice(String question7CChoice) {
    this.question7CChoice = question7CChoice;
  }

  public String getQuestion7DChoice() {
    return question7DChoice;
  }

  public void setQuestion7DChoice(String question7DChoice) {
    this.question7DChoice = question7DChoice;
  }

  public String getQuestion10BChoice() {
    return question10BChoice;
  }

  public void setQuestion10BChoice(String question10BChoice) {
    this.question10BChoice = question10BChoice;
  }

  public String getQuestion10CChoice() {
    return question10CChoice;
  }

  public void setQuestion10CChoice(String question10CChoice) {
    this.question10CChoice = question10CChoice;
  }

  public String getQuestion11AChoice() {
    return question11AChoice;
  }

  public void setQuestion11AChoice(String question11AChoice) {
    this.question11AChoice = question11AChoice;
  }

  @Override
  public String toString() {
    if(Utility.isCalledFromLogs(Thread.currentThread())){
      return Utility.toString(this);
    }
    return "NeoAggregatorMedicalQuestions{" +
            "question1CChoice='" + question1CChoice + '\'' +
            ", question1DChoice='" + question1DChoice + '\'' +
            ", question4EChoice='" + question4EChoice + '\'' +
            ", question4FChoice='" + question4FChoice + '\'' +
            ", question4GChoice='" + question4GChoice + '\'' +
            ", question5DChoice='" + question5DChoice + '\'' +
            ", question7BChoice='" + question7BChoice + '\'' +
            ", question7CChoice='" + question7CChoice + '\'' +
            ", question7DChoice='" + question7DChoice + '\'' +
            ", question10BChoice='" + question10BChoice + '\'' +
            ", question10CChoice='" + question10CChoice + '\'' +
            ", question11AChoice='" + question11AChoice + '\'' +
            '}';
  }
}
