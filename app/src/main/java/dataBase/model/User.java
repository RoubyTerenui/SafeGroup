package dataBase.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User  {

    // --- FIELDS ---
    private String nickname;
    private String uid;
    private String e_mail;
    @Nullable
    private String urlPicture;
    private String wifi;
    private List<Map<String,Object>>  groups;



    // --- CONSTRUCTORS ---

    public User(){
        this.nickname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.e_mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        this.groups= new ArrayList<Map<String,Object>>();
    }


    public User(String nickname, String uid, String e_mail, @Nullable String urlPicture ) {
        this.nickname = nickname;
        this.uid = uid;
        this.e_mail = e_mail;
        this.urlPicture = urlPicture;
        this.groups = new ArrayList<Map<String,Object>>();
    }

    // --- GETTERS ---

    public String getNickname() {        return nickname;    }
    public String getUid(){     return uid;     }
    public String getE_mail() {        return e_mail;    }
    @Nullable
    public String getUrlPicture() {        return urlPicture;    }
    public List<Map<String,Object>> getGroups() {        return groups;    }
    public String getWifi(){return wifi;}

    // --- SETTERS ---

    public void setNickname(String nickname) {        this.nickname = nickname;    }
    public void setUid(String uid) {    this.uid= uid;  }
    public void setE_mail(String e_mail) {        this.e_mail = e_mail;    }
    public void setUrlPicture(@Nullable String urlPicture) {        this.urlPicture = urlPicture;    }
    public void setGroups(List<Map<String,Object>> groups) {        this.groups = groups;    }
    public void setWifi(String bssid){ this.wifi = bssid;}

    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    // --- METHOD THAT PUSH A USER TO THE DATABASE ---
    // --- BY THE LAST USER PUSHED ----
    // --- IF A USER ALREADY EXIST HE WILL BE REPLACED ---

    public void pushUser_toDataBase() {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("nickname",this.nickname);
        ITEM_MAP.put("e_mail",this.e_mail);
        ITEM_MAP.put("groups",this.groups);
        ITEM_MAP.put("wifi", this.wifi);
        if(this.urlPicture!=null) {
            ITEM_MAP.put("urlPicture", this.urlPicture);
        }
        mDatabase.child("users").child(this.uid).setValue(ITEM_MAP);
    }


    // --- METHOD THAT ADD A GROUP TO AN ALREADY EXISTING USER ---
    // --- DOESN'T OVERWRITE EXISTING GROUPS ---
    public void pushnewGroupUser(Group newGroup){
        /**
         * A map of the Item you want to add
         */
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        Map<String,Object> newGroupUser=new HashMap<String,Object>();
        newGroupUser.put("favorite", false);
        newGroupUser.put("party", false);
        newGroupUser.put("name",newGroup.getName());
        newGroupUser.put("group_id",newGroup.getGr_id());
        ITEM_MAP.put(newGroup.getGr_id(),newGroupUser);
        mDatabase.child("users").child(this.uid).child("groups").updateChildren(ITEM_MAP);


    }



}
