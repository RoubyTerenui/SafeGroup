package dataBase.model;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Group {

    // --- FIELDS ---

    private String gr_id;
    private String name;
    private String administrator ;
    private List<Integer> typeOfGroup ;
    private List<String> members;
    private DatabaseReference mDatabase;

    // --- CONSTRUCTORS ---

    public Group(Group group,DatabaseReference mDatabase) {
        this.gr_id = group.getGr_id();
        this.name = group.getName();
        this.administrator = group.getAdministrator();
        this.typeOfGroup = group.getTypeOfGroup();
        this.members=group.getMembers();
        this.mDatabase = mDatabase ;
    }

    public Group(){};

    public Group(String gr_id, String name, String administrator, List<Integer> typeOfGroup ,DatabaseReference mDatabase) {
        this.gr_id = gr_id;
        this.name = name;
        this.administrator = administrator;
        this.typeOfGroup = typeOfGroup;
        this.members=new ArrayList<String>();
        this.members.add(administrator);
        this.mDatabase = mDatabase ;
    }



    // --- GETTERS ---
    public String getName() {        return name;    }
    public List<Integer> getTypeOfGroup() {        return typeOfGroup;    }
    public String getAdministrator() {        return administrator;    }
    public String getGr_id(){     return gr_id;    }
    public List<String> getMembers() {        return members;    }

    // --- SETTERS ---
    public void setAdministrator(String administrator) {        this.administrator = administrator;    }
    public void setName(String name) {        this.name = name;    }
    public void setTypeOfGroup(List<Integer> typeOfGroup) {        this.typeOfGroup = typeOfGroup;    }
    public void setGr_id(String gr_id){     this.gr_id =gr_id;    }
    public void setMembers(List<String> members) {        this.members = members;    }

// ---TO PUSH DATA in the DATABASE---

    public void pushGroup_toDataBase(){
        mDatabase.child("groups").child(this.gr_id).setValue(this);
    }
}


