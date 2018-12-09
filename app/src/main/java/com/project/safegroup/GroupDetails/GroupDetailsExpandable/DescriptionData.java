package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

public class DescriptionData {

    String date;
    int state;
    boolean asked;

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
