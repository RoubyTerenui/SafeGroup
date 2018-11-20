package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

public class MemberData {

    String name;
    String Id;
    int state;

    public MemberData(String name,String Id,int state ) {
        this.name=name;
        this.Id=Id;
        this.state=state;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return Id;
    }
    public int getState(){return state;}
}
