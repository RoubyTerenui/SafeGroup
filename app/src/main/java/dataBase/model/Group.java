package dataBase.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {

    // --- FIELDS ---

    private String gr_id;
    private String name;
    private String administrator ;
    private List<Member> members;

    // --- CONSTRUCTORS ---
    public Group(){

    }
    public Group(Group group) {
        this.gr_id = group.getGr_id();
        this.name = group.getName();
        this.administrator = group.getAdministrator();
        this.members=new ArrayList<Member>();
        this.members.add(new Member(administrator,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),0));
    }


    public Group(String gr_id, String name, String administrator) {
        this.gr_id = gr_id;
        this.name = name;
        this.administrator = administrator;
        this.members=new ArrayList<Member>();
        this.members.add(new Member(administrator,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),0));
    }



    // --- GETTERS ---
    public String getName() {        return name;    }
    public String getAdministrator() {        return administrator;    }
    public String getGr_id(){     return gr_id;    }
    public List<Member> getMembers() {        return members;    }

    // --- SETTERS ---
    public void setAdministrator(String administrator) {        this.administrator = administrator;    }
    public void setName(String name) {        this.name = name;    }
    public void setGr_id(String gr_id){     this.gr_id =gr_id;    }
    public void setMembers(List<Member> members) {        this.members = members;    }

// ---TO PUSH DATA in the DATABASE---

    // --- METHOD THAT PUSH A GROUP TO THE DATABASE ---

    public void pushGroup_toDataBase() {

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("administrator",this.administrator);
        ITEM_MAP.put("name",this.name);
        ITEM_MAP.put("gr_id", this.gr_id);
        mDatabase.child("group").child(this.gr_id).setValue(ITEM_MAP);
        for (Member member:this.getMembers()             ) {
                member.pushMember_toDataBase(this.gr_id);
        }
    }
}


