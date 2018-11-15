package dataBase.model;

public class Group {

    // --- FIELDS ---

    private String name;
    private String gid;
    private User administrator ;
    //@Nullable(pour une photo de profil)
    //private String urlPicture;
    private int typeOfGroup ;
    //Rajouter une Liste de User(non identifié par leurs ID)
    // --- CONSTRUCTORS ---

    public Group() {    }

    public Group(String name, String gid, User administrator, int typeOfGroup) {
        this.name = name;
        this.gid = gid;
        this.administrator = administrator;
        this.typeOfGroup = typeOfGroup;
    }



    // --- GETTERS ---
    public String getName() {        return name;    }
    public int getTypeOfGroup() {        return typeOfGroup;    }
    public User getAdministrator() {        return administrator;    }
    public String getGid(){     return gid;    }

    // --- SETTERS ---
    public void setAdministrator(User administrator) {        this.administrator = administrator;    }
    public void setName(String name) {        this.name = name;    }
    public void setTypeOfGroup(int typeOfGroup) {        this.typeOfGroup = typeOfGroup;    }
    public void SetGid(String gid){     this.gid=gid;    }



}
