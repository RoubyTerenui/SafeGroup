package dataBase.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Member {

    // --- FIELDS ---

    private String user_id;
    private String group_id;
    private Date last_Update;
    private int state;
    private int state_Precision;
    private double latitude;
    private double longitude;
    private DatabaseReference mDatabase;

    // --- CONSTRUCTORS ---

    public Member(){}

    public Member(Member member,DatabaseReference mDatabase){
        this.user_id = member.getUser_Id();
        this.group_id = member.getGroup_Id();
        this.last_Update = member.getLast_Update();
        this.state = member.getState();
        this.state_Precision = member.getState_Precision();
        this.latitude = member.getLatitude();//TO DO decider si mieux dans User
        this.longitude = member.getLongitude();
        this.mDatabase = mDatabase;
    }
    public Member(String user_Id, String group_id, Date last_Update, int state, int state_Precision, double latitude, double longitude, DatabaseReference mDatabase) {
        this.user_id = user_Id;
        this.group_id = group_id;
        this.last_Update = last_Update;
        this.state = state;
        this.state_Precision = state_Precision;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mDatabase =mDatabase ;
    }


    // --- GETTERS ---

    public String getUser_Id() {        return user_id;    }
    public String getGroup_Id() {        return group_id;    }
    public Date getLast_Update() {        return last_Update;    }
    public int getState() {        return state;    }
    public int getState_Precision() {        return state_Precision;    }
    public double getLatitude() {        return latitude;    }
    public double getLongitude() {        return longitude;    }


    // --- SETTERS ---

    public void setUser_Id(String user_Id) {        this.user_id = user_Id;    }
    public void setGroup_Id(String group_id) {        this.group_id = group_id;    }
    public void setLast_Update(Date last_Update) {        this.last_Update = last_Update;    }
    public void setState(int state) {        this.state = state;    }
    public void setState_Precision(int state_Precision) {        this.state_Precision = state_Precision;    }
    public void setLatitude(double latitude) {        this.latitude = latitude;    }
    public void setLongitude(double longitude) {        this.longitude = longitude;    }


    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    public void pushMember_toDataBase(){
        mDatabase.child("members").child(this.group_id).setValue(this);
    }


}


