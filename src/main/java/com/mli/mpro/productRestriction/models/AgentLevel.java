package com.mli.mpro.productRestriction.models;

public enum AgentLevel {
    BASE("Base",0), LEVEL_1("Level 1", 1), LEVEL_2("Level 2", 2), LEVEL_3("Level 3", 3), LEVEL_4("Level 4", 4),
    LEVEL_5("Level 5", 5), LEVEL_6("Level 6", 6), LEVEL_7("Level 7", 7);

    String code;
    int value;

    AgentLevel(String code, int value){
        this.code = code;
        this.value = value;
    }

    public static int getValue(String code){
        for(AgentLevel agentLevel : AgentLevel.values()){
            if(agentLevel.code.equalsIgnoreCase(code))
                return agentLevel.value;
        }
        return Integer.MAX_VALUE;
    }
}
