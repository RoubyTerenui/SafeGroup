package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import dataBase.model.OtherState;
import dataBase.model.SelfState;

public class DescriptionData {

    // --- SETTERS ---

    String name;
    String date;
    int state;

    SelfState selfState;
    OtherState otherState;
    boolean asked;

    // --- CONSTRUCTOR ---

    public DescriptionData(int state, SelfState selfState, OtherState otherState, String name, String date,boolean asked) {
        this.otherState = otherState;
        this.selfState = selfState;
        this.state = state;
        this.name = name;
        this.date = date;
        this.asked = asked;
    }
    // --- GETTERS ---

    public String getName(){
        return name;
    }
    public int getState(){
        return state;
    }
    public String getDate(){return this.date;}
    public SelfState getSelfState() {return selfState;}
    public OtherState getOtherState() {return otherState;}
    public boolean isAsked(){return this.asked;}
}
