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
    private Boolean asked;
    private OtherState otherState;
    private SelfState selfState;
    private int state;


    // --- CONSTRUCTORS ---

    public Member(){
        this.member_Id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.name=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        //this.last_Update =new Date().toString();
        this.state=0;
    }
    public Member(Member member){
        this.member_Id = member.getMember_Id();
        this.state = member.getState();
    }

    public Member(String member_Id,String nameId,String name, int state, int state_Precision) {
        this.name=name;
        this.member_Id=member_Id;
        this.state = state;
    }


    // --- GETTERS ---
    public String getMember_Id() {        return member_Id;    }
    public int getState() {        return state;    }
    public String getName() {        return name;    }
    public OtherState getOtherState() {        return otherState;    }
    public SelfState getSelfState() {        return selfState;    }
    public Boolean getAsked() {        return asked;    }


    // --- SETTERS ---
    public void setMember_Id(String member_Id) {        this.member_Id = member_Id;    }
    public void setState(int state) {        this.state = state;    }
    public void setName(String name) {        this.name = name;    }
    public void setOtherState(OtherState otherState) {        this.otherState = otherState;    }
    public void setSelfState(SelfState selfState) {        this.selfState = selfState;    }
    public void setAsked(Boolean asked) {        this.asked = asked;    }

    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    // --- METHOD THAT PUSH A MEMBER TO THE DATABASE ---
    // --- BY THE LAST MEMBER PUSHED ----
    // --- IF A MEMBER ALREADY EXIST HE WILL BE REPLACED ---
    public void pushMember_toDataBase(String group_id) {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("state",this.state);
        ITEM_MAP.put("asked",this.asked);
        ITEM_MAP.put("name", this.name);
        mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()).setValue(ITEM_MAP);
        otherState.pushOtherState_toDataBase(mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()));
        selfState.pushSelfState_toDataBase(mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()));

    }

    // --- METHOD THAT CHANGE THE STATE OF A MEMBER ---
    public void changeState_Member(String group_id,String member_Id,int state,@Nullable int stateDescription){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("group").child(group_id).child("members");
        if (FirebaseAuth.getInstance().getCurrentUser().getUid()==member_Id){//Si l'utilisateur actuel à le meme id propre etat
            SelfState newselfState=new SelfState(state,stateDescription);
            newselfState.pushSelfState_toDataBase(mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()));
        }
        else{
            OtherState newselfState=new OtherState(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),state);
            otherState.pushOtherState_toDataBase(mDatabase.child("group").child(group_id).child("members").child(this.getMember_Id()));
        }
    }
}


