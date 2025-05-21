
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cardio", "hormonal", "respiratory", "bloodAndCellular", "digestiveAndRegulatory", "mentalAndPsychiatric", "neuralOrSkeletalOrMuscular",
	"infectiousOrContagious", "otherMedicalConditions", "hospitalizationAndAbsenceFromWork" })
public class DiagnosedOrTreatedDetails {

    @JsonProperty("cardio")
    private Cardio cardio;
    @JsonProperty("hormonal")
    private Hormonal hormonal;
    @JsonProperty("respiratory")
    private Respiratory respiratory;
    @JsonProperty("bloodAndCellular")
    private BloodAndCellular bloodAndCellular;
    @JsonProperty("digestiveAndRegulatory")
    private DigestiveAndRegulatory digestiveAndRegulatory;
    @JsonProperty("mentalAndPsychiatric")
    private MentalAndPsychiatric mentalAndPsychiatric;
    @JsonProperty("neuralOrSkeletalOrMuscular")
    private NeuralOrSkeletalOrMuscular neuralOrSkeletalOrMuscular;
    @JsonProperty("infectiousOrContagious")
    private InfectiousOrContagious infectiousOrContagious;
    @JsonProperty("otherMedicalConditions")
    private OtherMedicalConditions otherMedicalConditions;
    @JsonProperty("hospitalizationAndAbsenceFromWork")
    private HospitalizationAndAbsenceFromWork hospitalizationAndAbsenceFromWork;
    @JsonProperty("kidneyDisorder")
    private KidneyDisorder kidneyDisorder;
    @JsonProperty("everAdvisedForSurgeryEcg")
    private EverAdvisedForSurgeryEcg everAdvisedForSurgeryEcg;
    @JsonProperty("externalInternalAnomaly")
    private ExternalInternalAnomaly externalInternalAnomaly;
    @JsonProperty("geneticTesting")
    private EverHadGeneticTesting geneticTesting;
    @JsonProperty("specifyHabit")
    private SpecifyHabit specifyHabit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DiagnosedOrTreatedDetails() {
    }

    /**
     * 
     * @param digestiveAndRegulatory
     * @param cardio
     * @param infectiousOrContagious
     * @param mentalAndPsychiatric
     * @param respiratory
     * @param hormonal
     * @param hospitalizationAndAbsenceFromWork
     * @param bloodAndCellular
     * @param neuralOrSkeletalOrMuscular
     * @param otherMedicalConditions
     */
    public DiagnosedOrTreatedDetails(Cardio cardio, Hormonal hormonal, Respiratory respiratory, BloodAndCellular bloodAndCellular,
	    DigestiveAndRegulatory digestiveAndRegulatory, MentalAndPsychiatric mentalAndPsychiatric, NeuralOrSkeletalOrMuscular neuralOrSkeletalOrMuscular,
	    InfectiousOrContagious infectiousOrContagious, OtherMedicalConditions otherMedicalConditions,
	    HospitalizationAndAbsenceFromWork hospitalizationAndAbsenceFromWork) {
	super();
	this.cardio = cardio;
	this.hormonal = hormonal;
	this.respiratory = respiratory;
	this.bloodAndCellular = bloodAndCellular;
	this.digestiveAndRegulatory = digestiveAndRegulatory;
	this.mentalAndPsychiatric = mentalAndPsychiatric;
	this.neuralOrSkeletalOrMuscular = neuralOrSkeletalOrMuscular;
	this.infectiousOrContagious = infectiousOrContagious;
	this.otherMedicalConditions = otherMedicalConditions;
	this.hospitalizationAndAbsenceFromWork = hospitalizationAndAbsenceFromWork;
    }

    @JsonProperty("cardio")
    public Cardio getCardio() {
	return cardio;
    }

    @JsonProperty("cardio")
    public void setCardio(Cardio cardio) {
	this.cardio = cardio;
    }

    @JsonProperty("hormonal")
    public Hormonal getHormonal() {
	return hormonal;
    }

    @JsonProperty("hormonal")
    public void setHormonal(Hormonal hormonal) {
	this.hormonal = hormonal;
    }

    @JsonProperty("respiratory")
    public Respiratory getRespiratory() {
	return respiratory;
    }

    @JsonProperty("respiratory")
    public void setRespiratory(Respiratory respiratory) {
	this.respiratory = respiratory;
    }

    @JsonProperty("bloodAndCellular")
    public BloodAndCellular getBloodAndCellular() {
	return bloodAndCellular;
    }

    @JsonProperty("bloodAndCellular")
    public void setBloodAndCellular(BloodAndCellular bloodAndCellular) {
	this.bloodAndCellular = bloodAndCellular;
    }

    @JsonProperty("digestiveAndRegulatory")
    public DigestiveAndRegulatory getDigestiveAndRegulatory() {
	return digestiveAndRegulatory;
    }

    @JsonProperty("digestiveAndRegulatory")
    public void setDigestiveAndRegulatory(DigestiveAndRegulatory digestiveAndRegulatory) {
	this.digestiveAndRegulatory = digestiveAndRegulatory;
    }

    @JsonProperty("mentalAndPsychiatric")
    public MentalAndPsychiatric getMentalAndPsychiatric() {
	return mentalAndPsychiatric;
    }

    @JsonProperty("mentalAndPsychiatric")
    public void setMentalAndPsychiatric(MentalAndPsychiatric mentalAndPsychiatric) {
	this.mentalAndPsychiatric = mentalAndPsychiatric;
    }

    @JsonProperty("neuralOrSkeletalOrMuscular")
    public NeuralOrSkeletalOrMuscular getNeuralOrSkeletalOrMuscular() {
	return neuralOrSkeletalOrMuscular;
    }

    @JsonProperty("neuralOrSkeletalOrMuscular")
    public void setNeuralOrSkeletalOrMuscular(NeuralOrSkeletalOrMuscular neuralOrSkeletalOrMuscular) {
	this.neuralOrSkeletalOrMuscular = neuralOrSkeletalOrMuscular;
    }

    @JsonProperty("infectiousOrContagious")
    public InfectiousOrContagious getInfectiousOrContagious() {
	return infectiousOrContagious;
    }

    @JsonProperty("infectiousOrContagious")
    public void setInfectiousOrContagious(InfectiousOrContagious infectiousOrContagious) {
	this.infectiousOrContagious = infectiousOrContagious;
    }

    @JsonProperty("otherMedicalConditions")
    public OtherMedicalConditions getOtherMedicalConditions() {
	return otherMedicalConditions;
    }

    @JsonProperty("otherMedicalConditions")
    public void setOtherMedicalConditions(OtherMedicalConditions otherMedicalConditions) {
	this.otherMedicalConditions = otherMedicalConditions;
    }

    @JsonProperty("hospitalizationAndAbsenceFromWork")
    public HospitalizationAndAbsenceFromWork getHospitalizationAndAbsenceFromWork() {
	return hospitalizationAndAbsenceFromWork;
    }

    @JsonProperty("hospitalizationAndAbsenceFromWork")
    public void setHospitalizationAndAbsenceFromWork(HospitalizationAndAbsenceFromWork hospitalizationAndAbsenceFromWork) {
	this.hospitalizationAndAbsenceFromWork = hospitalizationAndAbsenceFromWork;
    }

    public KidneyDisorder getKidneyDisorder() {
        return kidneyDisorder;
    }

    public DiagnosedOrTreatedDetails setKidneyDisorder(KidneyDisorder kidneyDisorder) {
        this.kidneyDisorder = kidneyDisorder;
        return this;
    }

    public EverAdvisedForSurgeryEcg getEverAdvisedForSurgeryEcg() {
        return everAdvisedForSurgeryEcg;
    }

    public DiagnosedOrTreatedDetails setEverAdvisedForSurgeryEcg(EverAdvisedForSurgeryEcg everAdvisedForSurgeryEcg) {
        this.everAdvisedForSurgeryEcg = everAdvisedForSurgeryEcg;
        return this;
    }

    public ExternalInternalAnomaly getExternalInternalAnomaly() {
        return externalInternalAnomaly;
    }

    public DiagnosedOrTreatedDetails setExternalInternalAnomaly(ExternalInternalAnomaly externalInternalAnomaly) {
        this.externalInternalAnomaly = externalInternalAnomaly;
        return this;
    }

    public EverHadGeneticTesting getGeneticTesting() {
        return geneticTesting;
    }

    public DiagnosedOrTreatedDetails setGeneticTesting(EverHadGeneticTesting geneticTesting) {
        this.geneticTesting = geneticTesting;
        return this;
    }

    public SpecifyHabit getSpecifyHabit() {
        return specifyHabit;
    }

    public DiagnosedOrTreatedDetails setSpecifyHabit(SpecifyHabit specifyHabit) {
        this.specifyHabit = specifyHabit;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "DiagnosedOrTreatedDetails{" +
                "cardio=" + cardio +
                ", hormonal=" + hormonal +
                ", respiratory=" + respiratory +
                ", bloodAndCellular=" + bloodAndCellular +
                ", digestiveAndRegulatory=" + digestiveAndRegulatory +
                ", mentalAndPsychiatric=" + mentalAndPsychiatric +
                ", neuralOrSkeletalOrMuscular=" + neuralOrSkeletalOrMuscular +
                ", infectiousOrContagious=" + infectiousOrContagious +
                ", otherMedicalConditions=" + otherMedicalConditions +
                ", hospitalizationAndAbsenceFromWork=" + hospitalizationAndAbsenceFromWork +
                ", kidneyDisorder=" + kidneyDisorder +
                ", everAdvisedForSurgeryEcg=" + everAdvisedForSurgeryEcg +
                ", externalInternalAnomaly=" + externalInternalAnomaly +
                ", geneticTesting=" + geneticTesting +
                ", specifyHabit=" + specifyHabit +
                '}';
    }
}
