package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.safegroup.R;

import java.util.Date;

import dataBase.model.OtherState;

public class StateDialog extends Dialog{

    public Activity activity;
    public Button greenButton,orangeButton,redButton,confirmButton,cancelButton;
    public int selected=4;
    public String name;
    public String idGroup;
    public boolean isAsked;

    public StateDialog(Activity activity,String name,String groupID) {
        super(activity);
        this.activity = activity;
        this.name=name;
        this.idGroup = groupID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_set_state);
        greenButton = (Button) findViewById(R.id.greenButton);
        orangeButton = (Button) findViewById(R.id.orangeButton);
        redButton = (Button) findViewById(R.id.redButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreenButton));
                orangeButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                redButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                selected=2;
            }
        });
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                orangeButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorOrangeButton));
                redButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                selected=1;
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                orangeButton.setBackgroundColor(getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
                redButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorRedButton));
                selected=0;
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=4){
                    sendMessage(selected);
                    dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void sendMessage(int selected){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("group");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Date date = new Date();
            DatabaseReference userReference = mDatabase.child(idGroup).child("members").child(name);
            userReference.child("last_Update").setValue(date.toString());
            userReference.child("state").setValue(selected);
            new OtherState(user.getDisplayName(),selected).pushOtherState_toDataBase(userReference);
    }
}
