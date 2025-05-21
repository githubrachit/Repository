package com.mli.mpro.proposal.models.irpPsmForNeo;

public class Fund {
    public String name;
    public String category;
    public Returns returns;
    public String allocation;

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Returns getReturns() {
        return returns;
    }
    public void setReturns(Returns returns) {
        this.returns = returns;
    }
    public String getAllocation() {
        return allocation;
    }
    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }
    //toString
    @Override
    public String toString() {
        return "Fund [allocation=" + allocation + ", category=" + category + ", name=" + name + ", returns="
                + returns + "]";
    }

}
