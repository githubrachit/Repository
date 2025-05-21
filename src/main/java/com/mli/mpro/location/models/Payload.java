package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.location.services.BranchCodeService;
import com.mli.mpro.utils.Utility;

public class Payload {
	
	private List<BranchCodeService> sourcingBranchCodes;

    public void setSourcingBranchCodes(List<BranchCodeService> sourcingBranchCodes){
        this.sourcingBranchCodes = sourcingBranchCodes;
    }
    public List<BranchCodeService> getSourcingBranchCodes(){
        return this.sourcingBranchCodes;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "sourcingBranchCodes=" + sourcingBranchCodes +
                '}';
    }
}