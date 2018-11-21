package dataBase.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Member {

    // --- FIELDS ---

    private String member_Id;
    private String name;
    private String nameModifier;
    private String last_Update;
    private int state;
    @Nullable
    private int state_Precision;

    // --- CONSTRUCTORS ---

    public Member(){
        this.member_Id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.nameModifier=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.last_Update =new Date().toString();
        this.state=0;
    }
    public Member(Member member){
        this.member_Id = member.getMember_Id();
        this.nameModifier=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.last_Update = member.getLast_Update();
        this.state = member.getState();
        this.state_Precision = member.getState_Precision();
    }

    public Member(String member_Id,String nameId,String name, String last_Update, int state, int state_Precision) {
        this.nameModifier=nameId ;
        this.name=name;
        this.member_Id=member_Id;
        this.last_Update = last_Update;
        this.state = state;
        this.state_Precision = state_Precision;
    }


    // --- GETTERS ---

    public String getMember_Id() {        return member_Id;    }
    public String getNameModifier()  {        return nameModifier;    }
    public String getLast_Update() {        return last_Update;    }
    public int getState() {        return state;    }
    public int getState_Precision() {        return state_Precision;    }



    // --- SETTERS ---

    public void setMember_Id(String member_Id) {        this.member_Id = member_Id;    }
    public void setNameModifier(String nameModifier)  {        this.nameModifier=nameModifier;    }
    public void setLast_Update(String last_Update) {        this.last_Update = last_Update;    }
    public void setState(int state) {        this.state = state;    }
    public void setState_Precision(int state_Precision) {        this.state_Precision = state_Precision;    }


    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    // --- METHOD THAT PUSH A MEMBER TO THE DATABASE ---
    // --- BY THE LAST MEMBER PUSHED ----
    // --- IF A MEMBER ALREADY EXIST HE WILL BE REPLACED ---
    public void pushMember_toDataBase(String group_id) {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("nameModifier",this.nameModifier);
        ITEM_MAP.put("state",this.state);
        ITEM_MAP.put("state_Precision",this.state_Precision);
        ITEM_MAP.put("name", this.name);
        ITEM_MAP.put("last_Update",this.last_Update);

        mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()).setValue(ITEM_MAP);

    }


}


