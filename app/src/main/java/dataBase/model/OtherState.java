package dataBase.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OtherState {

    // --- FIELD ---
    private String name;
    private int state;
    private String last_Update;

    // --- CONSTRUCTOR ---
    public OtherState() {
        this.name=null;
        this.state = 0;
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
        this.last_Update=format.format(new Date());;
    }
    public OtherState(String name, int state) {
        this.name=name;
        this.state = state;
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
        this.last_Update =format.format(new Date());;
    }

    // --- GETTERS ---
    public int getState() {
        return state;
    }
    public String getName() {
        return name;
    }
    public String getLast_Update() {
        return last_Update;
    }

    // --- SETTERS ---
    public void setState(int state) {
        this.state = state;
    }
    public void setLast_Update(String last_Update) {
        this.last_Update = last_Update;
    }
    public void setName(String name) {
        this.name = name;
    }

    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    // --- Modifiy thee OtherState (which ---
    // --- correspond to the State modify ---
    // --- by other users) ---


    public void pushOtherState_toDataBase(DatabaseReference mDatabase) {
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("state",this.state);
        ITEM_MAP.put("name",this.name);
        ITEM_MAP.put("date",this.last_Update);
        mDatabase.child("otherState").setValue(ITEM_MAP);
    }
}
