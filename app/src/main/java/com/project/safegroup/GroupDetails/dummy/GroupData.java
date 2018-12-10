package com.project.safegroup.GroupDetails.dummy;

public class GroupData {
    // --- FIELDS ---

    private String groupName;
    private String groupID;
    private boolean favorite;
    private boolean party;
    // --- CONSTRUCTORS ---
    public GroupData(){

    }
    public GroupData(String groupName,String groupID,boolean favorite,boolean party) {
        this.groupName=groupName;
        this.groupID=groupID;
        this.favorite = favorite;
        this.party = party;
    }



    // --- GETTERS ---
    public String getGroupName() {        return groupName;    }
    public String getGroupID()     {return groupID;     }
    public boolean isFavorite(){return favorite;}
    public boolean isParty(){return party;}

    // --- SETTERS ---
    public void setGroupName(String name) {        this.groupName = name;    }
    public void setGroupID(String id){this.groupID=id;}
}
