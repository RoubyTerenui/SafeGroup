package dataBase.model;

import android.support.annotation.Nullable;

public class User {

    // --- FIELDS ---

    private String userName;
    private int nbrGrp;
    private String uid;
    @Nullable
    private String urlPicture;

    // --- CONSTRUCTORS ---

    public User(){ }

    public User( String username, String uid) {
        this.userName = userName;
        this.uid = uid;
        this.nbrGrp=0;
    }

// --- GETTERS ---

    public String getUserName() {        return userName ;    }
    public String getUid(){     return uid;     }
    public int getNbrGrp(){      return nbrGrp;      }

    // --- SETTERS ---

    public void setNickname(String userName) {        this.userName = userName;    }
    public void setUid(String uid) {    this.uid= uid;  }
    public void setNbrGrp(int   nbrGrp){    this.nbrGrp=nbrGrp; }
}
