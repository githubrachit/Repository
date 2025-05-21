package com.mli.mpro.onboarding.pathflex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrmsBrokerOutput {

    private String classIsArray;
    private String className;

    @JsonProperty("pathflex_input")
    private PathflexInput pathflexInput;
    @JsonProperty("pathflex_output_utm")
    private PathflexOutputUtm pathflexOutputUtm;

    public String getClassIsArray() {
        return classIsArray;
    }

    public void setClassIsArray(String classIsArray) {
        this.classIsArray = classIsArray;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public PathflexInput getPathflexInput() {
        return pathflexInput;
    }

    public void setPathflexInput(PathflexInput pathflexInput) {
        this.pathflexInput = pathflexInput;
    }

    public PathflexOutputUtm getPathflexOutputUtm() {
        return pathflexOutputUtm;
    }

    public void setPathflexOutputUtm(PathflexOutputUtm pathflexOutputUtm) {
        this.pathflexOutputUtm = pathflexOutputUtm;
    }

    @Override
    public String toString() {
        return "BrmsBrokerOutput{" +
                "classIsArray='" + classIsArray + '\'' +
                ", className='" + className + '\'' +
                ", pathflexInput=" + pathflexInput +
                ", pathflexOutputUtm=" + pathflexOutputUtm +
                '}';
    }
}
