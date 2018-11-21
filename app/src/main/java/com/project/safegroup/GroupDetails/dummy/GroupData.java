package com.project.safegroup.GroupDetails.dummy;

public class GroupData {
    // --- FIELDS ---

    private String adminName;
    private String groupName;
    private String groupID;
    // --- CONSTRUCTORS ---
    public GroupData(){

    }
    public GroupData(String adminName, String groupName,String groupID) {
        this.groupName=groupName;
        this.adminName=adminName;
        this.groupID=groupID;
    }



    // --- GETTERS ---
    public String getAdminName() {        return adminName;    }
    public String getGroupName() {        return groupName;    }
    public String getGroupID()     {return groupID;             }

    // --- SETTERS ---
    public void setAdminName(String administrator) {        this.adminName= administrator;    }
    public void setGroupName(String name) {        this.groupName = name;    }
    public void setGroupID(String id){this.groupID=id;}
}
