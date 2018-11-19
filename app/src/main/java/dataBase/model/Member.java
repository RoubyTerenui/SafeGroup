package dataBase.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Member {

    // --- FIELDS ---

    //private String member_Id;
    //private String user_Id;
    @Nullable
    private Date last_Update;
    private int state;
    private int state_Precision;
    @Nullable
    private double latitude;
    @Nullable
    private double longitude;
    private DatabaseReference mDatabase;

    // --- CONSTRUCTORS ---

    public Member(Member member,DatabaseReference mDatabase){
        //this.user_Id = member.getUser_Id();
        //this.member_Id = member.getMember_Id();
        this.last_Update = member.getLast_Update();
        this.state = member.getState();
        this.state_Precision = member.getState_Precision();
        this.latitude = member.getLatitude();//TO DO decider si mieux dans User
        this.longitude = member.getLongitude();
        this.mDatabase = mDatabase;
    }
    public Member(String user_Id, String member_Id, @Nullable Date last_Update, int state, int state_Precision, double latitude, double longitude, DatabaseReference mDatabase) {
        //this.user_Id = user_Id;
        //this.member_Id = member_Id;
        this.last_Update = last_Update;
        this.state = state;
        this.state_Precision = state_Precision;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mDatabase =mDatabase ;
    }


    // --- GETTERS ---

    //public String getUser_Id() {        return user_Id;    }
    //public String getMember_Id() {        return member_Id;    }
    public Date getLast_Update() {        return last_Update;    }
    public int getState() {        return state;    }
    public int getState_Precision() {        return state_Precision;    }
    public double getLatitude() {        return latitude;    }
    public double getLongitude() {        return longitude;    }


    // --- SETTERS ---

    //public void setUser_Id(String user_Id) {        this.user_Id = user_Id;    }
    //public void setMember_Id(String member_Id) {        this.member_Id = member_Id;    }
    public void setLast_Update(Date last_Update) {        this.last_Update = last_Update;    }
    public void setState(int state) {        this.state = state;    }
    public void setState_Precision(int state_Precision) {        this.state_Precision = state_Precision;    }
    public void setLatitude(double latitude) {        this.latitude = latitude;    }
    public void setLongitude(double longitude) {        this.longitude = longitude;    }


    // ---METHODS---


    // ---TO PUSH DATA in the DATABASE---

    public void pushMember_toDataBase(String memberId){
        mDatabase.child("members").child(memberId).setValue(this);
    }


}


