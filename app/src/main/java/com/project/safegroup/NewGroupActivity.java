package com.project.safegroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dataBase.model.Group;
import dataBase.model.User;

public class NewGroupActivity extends AppCompatActivity {

    private Button addButton;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        addButton=(Button) findViewById(R.id.buttonNewGroup);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPostKey = FirebaseDatabase.getInstance().getReference().child("group").push().getKey();
                editText=(EditText)findViewById(R.id.editTextNewGroup);
                Group group=new Group(newPostKey,editText.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid());
                group.pushGroup_toDataBase();
                User userDummy=new User();
                userDummy.pushnewGroupUser(group);
                finish();
            }
        });
    }
    @Override
    public void finish(){
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }
}
