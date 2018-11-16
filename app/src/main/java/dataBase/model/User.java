package dataBase.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        this.nickname = "null";
        this.uid = "null";
        this.e_mail = "null";
    }


    public User(String nickname, String uid, String e_mail, @Nullable String urlPicture , DatabaseReference mDatabase) {
        this.nickname = nickname;
        this.uid = uid;
        this.e_mail = e_mail;
        this.urlPicture = urlPicture;
        this.listGroupId = new ArrayList<String>();
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

    public void pushUser_toDataBase() {
        mDatabase.child("users").child(this.uid).setValue(this);
    }



}
