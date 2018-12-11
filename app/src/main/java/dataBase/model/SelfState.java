package dataBase.model;

import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelfState {

    // --- FIELD ---
    private int state;
    @Nullable
    private int stateDescription;
    private String last_Update;

    // --- CONSTRUCTOR ---

    public SelfState(){
        this.state = 0;
        this.stateDescription = 0;
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
        this.last_Update =format.format(new Date());
    }

    public SelfState(int state, @Nullable int stateDescription) {
        this.state = state;
        this.stateDescription = stateDescription;
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
        this.last_Update =format.format(new Date());
    }

    // --- GETTERS ---

    public int getStateDescription() {
        return stateDescription;
    }
    public int getState() {
        return state;
    }
    public String getLast_Update() {
        return last_Update;
    }

    // --- SETTERS ---

    public void setStateDescription(int stateDescription) {
        this.stateDescription = stateDescription;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setLast_Update(String last_Update) {
        this.last_Update = last_Update;
    }

    // ---TO PUSH DATA in the DATABASE---

    // --- Modifiy thee SelfState (which ---
    // --- correspond to the user State) ---

    public void pushSelfState_toDataBase(DatabaseReference mDatabase) {
        Map<String,Object > ITEM_MAP = new HashMap<String, Object>();
        ITEM_MAP.put("state",this.state);
        ITEM_MAP.put("stateDescription",this.stateDescription);
        ITEM_MAP.put("date",this.last_Update);
        mDatabase.child("selfState").setValue(ITEM_MAP);
    }
}
