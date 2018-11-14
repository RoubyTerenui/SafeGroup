package dataBase.model;

import android.support.annotation.Nullable;

public class User {

    // --- FIELDS ---

    private String firstname;
    private String lastname;
    private String nickname;
    private String uid;
    @Nullable
    private String urlPicture;
//TO DO ajouter une classe USER Differente qui pourra differer selon les groupes
    // --- CONSTRUCTORS ---

    public User(){ }

    public User(String firstname, String lastname, String nickname, String uid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.uid = uid;
    }

// --- GETTERS ---

    public String getFirstname() {        return firstname;    }
    public String getLastname() {        return lastname;    }
    public String getNickname() {        return nickname;    }
    public String getUid(){     return uid;     }

    // --- SETTERS ---

    public void setFirstname(String firstname) {        this.firstname = firstname;    }
    public void setLastname(String lastname) {        this.lastname = lastname;    }
    public void setNickname(String nickname) {        this.nickname = nickname;    }
    public void setUid(String uid) {    this.uid= uid;  }
}
