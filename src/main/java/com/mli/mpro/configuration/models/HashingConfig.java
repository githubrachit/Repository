package com.mli.mpro.configuration.models;

public class HashingConfig {
    private boolean isHashingEnabled;
    private String uniqueSalt;
    private String algorithm;

    public String getUniqueSalt() {
        return uniqueSalt;
    }

    public void setUniqueSalt(String uniqueSalt) {
        this.uniqueSalt = uniqueSalt;
    }

    public boolean isHashingEnabled() {
        return isHashingEnabled;
    }

    public void setHashingEnabled(boolean hashingEnabled) {
        isHashingEnabled = hashingEnabled;
    }
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "HashingConfig{" +
                "isHashingEnabled=" + isHashingEnabled +
                ", uniqueSalt='" + uniqueSalt + '\'' +
                ", algorithm='" + algorithm + '\'' +
                '}';
    }
}
