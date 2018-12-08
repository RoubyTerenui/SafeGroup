package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import dataBase.model.OtherState;
import dataBase.model.SelfState;

public class DescriptionData {

    // --- SETTERS ---

    String name;
    //Boolean isSelf;
    int state;
    SelfState selfState;
    OtherState otherState;

    // --- CONSTRUCTOR ---

    public DescriptionData(int state, SelfState selfState, OtherState otherState, String name/*, Boolean isSelf*/){
        this.otherState=otherState;
        this.selfState=selfState;
        this.state=state;
        this.name=name;
        //this.isSelf=isSelf;
    }


    // --- GETTERS ---

    public String getName(){
        return name;
    }
    public int getState(){
        return state;
    }
    //public Boolean getSelf() {        return isSelf;    }
    public SelfState getSelfState() {        return selfState;    }
    public OtherState getOtherState() {        return otherState;    }
    //public Boolean getIsSelf(){return isSelf;}
}
