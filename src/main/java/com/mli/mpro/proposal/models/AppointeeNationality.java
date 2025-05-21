package com.mli.mpro.proposal.models;


public class AppointeeNationality {
    private String nationality;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Nationality{" +
                "nationality='" + nationality + '\'' +
                '}';
    }
}
