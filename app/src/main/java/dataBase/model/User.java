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
    private List<String> listGroupId ;
    private DatabaseReference mDatabase;



    // --- CONSTRUCTORS ---

    public User(){
        this.nickname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.e_mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        this.urlPicture = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        this.listGroupId = new ArrayList<String>();
    }


    public User(String nickname, String uid, String e_mail, @Nullable String urlPicture , DatabaseReference mDatabase) {
        this.nickname = nickname;
        this.uid = uid;
        this.e_mail = e_mail;
        this.urlPicture = urlPicture;
        this.listGroupId = new ArrayList<String>();
        this.listGroupId.add("group1");
        this.listGroupId.add("group2");
        this.mDatabase = mDatabase ;
    }

    // --- GETTERS ---

    public String getNickname() {        return nickname;    }
    public String getUid(){     return uid;     }
    public String getE_mail() {        return e_mail;    }
    @Nullable
    public String getUrlPicture() {        return urlPicture;    }
    public List<String> getListGroupId() {        return listGroupId;    }

    // --- SETTERS ---

    public void setNickname(String nickname) {        this.nickname = nickname;    }
    public void setUid(String uid) {    this.uid= uid;  }
    public void setE_mail(String e_mail) {        this.e_mail = e_mail;    }
    public void setUrlPicture(@Nullable String urlPicture) {        this.urlPicture = urlPicture;    }
    public void setListGroupId(List<String> listGroupId) {        this.listGroupId = listGroupId;    }

    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    // --- METHOD THAT PUSH A USER TO THE DATABASE ---
    // --- IF A USER ALREADY EXIST HE WILL BE REPLACED ---
    // --- BY THE LAST USER PUSHED ----
    public void pushUser_toDataBase() {
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("nickname",this.nickname);
        ITEM_MAP.put("e_mail",this.e_mail);
        ITEM_MAP.put("listGroupId",this.listGroupId);
        if(this.urlPicture!=null) {
            ITEM_MAP.put("urlPicture", this.urlPicture);
        }
        mDatabase.child("users").child(this.uid).setValue(ITEM_MAP);
    }


    // --- METHOD THAT ADD A GROUP TO AN ALREADY EXISTING USER ---
    // --- DOESN'T OVERWRITE EXISTING GROUPS ---
    public void pushnewGroupUser(String newGroup){
        /**
         * A map of the Item you want to add
         */
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        // --- GET A KEY FOR A NEW GROUP ---
        String newPostKey = mDatabase.child(this.uid).child("listGroupId").push().getKey();

        ITEM_MAP.put(newPostKey,newGroup);
        mDatabase.child("users").child(this.uid).child("listGroupId").updateChildren(ITEM_MAP);

    }



}
