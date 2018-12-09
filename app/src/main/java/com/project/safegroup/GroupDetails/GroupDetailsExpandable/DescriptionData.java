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
    boolean asked;

    // --- CONSTRUCTOR ---

    public DescriptionData(int state, SelfState selfState, OtherState otherState, String name/*, Boolean isSelf*/){
        this.otherState=otherState;
        this.selfState=selfState;
        this.state=state;
        this.name=name;
        //this.isSelf=isSelf;
    String otherDate;
    String otherName;
    int otherState;

    int selfState;
    String selfDate;
    int selfStatePrecision;

    boolean selfDescribed;
    boolean otherDescribed;

    public DescriptionData(    String date,int state,boolean asked,String otherDate,String otherName,int otherState,int selfState,String selfDate,int selfStatePrecision,boolean selfDescribed,boolean otherDescribed){
        this.date=date;
        this.state = state;
        this.asked = asked;
        this.otherDate = otherDate;
        this.otherName=otherName;
        this.otherState = otherState;
        this.selfState = selfState;
        this.selfStatePrecision = selfStatePrecision;
        this.selfDate = selfDate;
        this.selfDescribed =  selfDescribed;
        this.otherDescribed =  otherDescribed;
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
    public String getDate() {return this.date;}
    public int getState(){return this.state;}
    public boolean isAsked(){return this.asked;}
    public String getOtherDate(){return  this.otherDate;}
    public String getOtherName(){return this.otherName;}
    public int getOtherState(){return  this.otherState;}
    public int getSelfState(){return this.selfState;}
    public String getSelfDate(){return this.selfDate;}
    public int getSelfStatePrecision(){return this.selfStatePrecision;}
    public boolean isSelfDescribed(){return this.selfDescribed;}
    public boolean isOtherDescribed(){return this.otherDescribed;}
}
