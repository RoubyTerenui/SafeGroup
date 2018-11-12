package dataBase.model;

public class Group {
    private String name;
    private User administrator ;
    //@Nullable(pour une photo de profil)
    //private String urlPicture;
    private int typeOfGroup ;

    public Group() {    }

    // --- GETTERS ---
    public String getName() {        return name;    }
    public int getTypeOfGroup() {        return typeOfGroup;    }
    public User getAdministrator() {        return administrator;    }

    // --- SETTERS ---
    public void setAdministrator(User administrator) {        this.administrator = administrator;    }
    public void setName(String name) {        this.name = name;    }
    public void setTypeOfGroup(int typeOfGroup) {        this.typeOfGroup = typeOfGroup;    }




}
