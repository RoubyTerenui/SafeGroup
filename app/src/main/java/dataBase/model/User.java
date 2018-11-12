package dataBase.model;

public class User {
    private String firstname;
    private String lastname;
    private String nickname;
    //@Nullable(pour une photo de profil)
    //private String urlPicture;
    private User(){ }

    // --- GETTERS ---

    public String getFirstname() {        return firstname;    }
    public String getLastname() {        return lastname;    }
    public String getNickname() {        return nickname;    }

    // --- SETTERS ---

    public void setFirstname(String firstname) {        this.firstname = firstname;    }
    public void setLastname(String lastname) {        this.lastname = lastname;    }
    public void setNickname(String nickname) {        this.nickname = nickname;    }
}
