package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

public class DescriptionData {

    String editorDate;
    String editorName;
    Boolean isSelf;
    int state;
    int statePrecision;

    public DescriptionData(int state,int statePrecision, String editorDate, String editorName, Boolean isSelf){
        this.editorDate=editorDate;
        this.statePrecision=statePrecision;
        this.state=state;
        this.editorName=editorName;
        this.isSelf=isSelf;
    }

    public String getEditorDate()
    {
        return editorDate;
    }
    public String getEditorName(){
        return editorName;
    }
    public int getState(){
        return state;
    }
    public int getStatePrecision(){
        return statePrecision;
    }
    public Boolean getIsSelf(){return isSelf;}
}
