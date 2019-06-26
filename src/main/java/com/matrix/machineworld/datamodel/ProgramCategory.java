package com.matrix.machineworld.datamodel;

public enum ProgramCategory {
    MAINTENANCE,
    ENFORCEMENT,
    DEVELOPMENT,
    BACKGROUND;


    public static ProgramCategory getByValue(int value){
        switch(value){
            case 1 : return MAINTENANCE;
            case 2 : return ENFORCEMENT;
            case 3 : return DEVELOPMENT;
            case 4 : return BACKGROUND;
            default: return null;
        }
    }
}
