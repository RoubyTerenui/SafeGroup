package com.project.safegroup;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.GroupDetailExpandableFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.safegroup.GroupDetails.GroupList;
import com.project.safegroup.GroupSelection.GroupSelection;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.GroupSelection.GroupSelectionDataAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import dataBase.model.SelfState;
import dataBase.model.User;

public class MainActivity extends AppCompatActivity {
    private Fragment mainFragment;
    private ProblemQuad problemQuad;
    private DangerQuad dangerQuad;
    private SafeQuad safeQuad;
    private GroupQuad groupQuad;
    private NotificationRecap notificationRecap;
    private GroupSelection groupSelection;
    private OptionFragment optionFragment;
    private GroupList groupList;
    //private SectionStatePageAdapter mSectionStatePageAdapter;
    private int localState;
    private int localStatePrecision;
    private int localGroup;
    // ---User connected to the app---
   // private User logged_User;
    // ---Reference to access to the DataBase---
    private DatabaseReference mDatabaseReference;


    //  ---Identifier for Sign-In/Sign-Out and DeleteUser---
    private static final int RC_SIGN_IN = 123;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(8);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(7);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        checkLogin();
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.configureAndShowMainFragment();
    }


    public void checkLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in.
            startSignInActivity();
        }
    }
    private void configureAndShowMainFragment(){

        mainFragment =  getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        problemQuad= new ProblemQuad();
        dangerQuad = new DangerQuad();
        safeQuad = new SafeQuad();
        groupQuad = new GroupQuad();
        notificationRecap = new NotificationRecap();
        groupSelection = new GroupSelection();
        optionFragment = new OptionFragment();
        groupList = new GroupList();
        if (mainFragment == null) {
            mainFragment = new ThreeButtons();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    public void setState(int state){ localState = state; }

    public void setStatePrecision(int statePrecision){
        localStatePrecision=statePrecision;
    }

    public void setGroup(int group){localGroup=group;}

    public void setFragment(int fragId){
        switch (fragId) {
            case 0:
                    getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,dangerQuad).addToBackStack("begin").commit();
                break;
            case 2:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,problemQuad).addToBackStack("begin").commit();
                break;
            case 3:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,safeQuad).addToBackStack("begin").commit();
                break;
            case 4:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,groupQuad).addToBackStack(null).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,groupSelection).addToBackStack(null).commit();
                break;
            case 6:
                getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,notificationRecap).addToBackStack("begin").commit();
                break;
            case 7 :
                getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,optionFragment).addToBackStack("begin").commit();
                break;
            case 8:
                getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,groupList).addToBackStack("begin").commit();
                break;
            default:
                break;
        }
    }


    public void setNotificationDetail(){
        Resources res = getResources();
        String[] state = res.getStringArray(R.array.state);
        String[] preciseState = res.getStringArray(R.array.precise_state);
        String[] group = res.getStringArray(R.array.group);
        Log.d("group","id-" +localGroup + " /result" + group[localGroup]);
        String detail = String.format(res.getString(R.string.notification_detail_message),state[localState], preciseState[localStatePrecision],group[localGroup]);
        TextView detailText = (TextView) findViewById(R.id.detail_notification);
        detailText.setText(detail);
    }

    public void setNotificationError(){
        Resources res = getResources();
        String detail = res.getString(R.string.notification_detail_error);
        TextView detailText = (TextView) findViewById(R.id.detail_notification);
        detailText.setText(detail);
    }

    public void sendGeneralNotificationTo(final boolean favorite,final boolean party){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("groups");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> groupIds=  new ArrayList<>();
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                    Log.d("favorite"," " + favorite +"/" +((boolean)data.child("favorite").getValue()));
                    if(favorite && !((boolean)data.child("favorite").getValue())){

                        break;
                    }
                    Log.d("party"," " + party +"/" +((boolean)data.child("party").getValue()));
                    if(party && !((boolean)data.child("party").getValue())){
                        break;
                    }
                    groupIds.add((String)data.child("group_id").getValue());
                }
                setGroup((favorite)?1:(party)?2:0);
                sendNotificationTo(groupIds);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
                setNotificationError();
            }
        });
    }

    public void sendNotificationTo(ArrayList<String> groupIds){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("group");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        for (String id:groupIds) {
            Date date = new Date();
            DatabaseReference userReference = mDatabase.child(id).child("members").child(user.getUid());

            userReference.child("last_Update").setValue(date.toString());
            userReference.child("state").setValue(localState);
            userReference.child("asked").setValue(false);
            SelfState selfState = new SelfState(localState, localStatePrecision);
            //userReference.child("selfState").child("state").setValue(localState);
            //userReference.child("selfState").child("state").setValue(localStatePrecision);
            selfState.pushSelfState_toDataBase(userReference);

            if(localState==0){
                //recuperer position GPS
            }
        }
        setFragment(6);
    }

    // --- Launch Sign-In Activity ---

    private void startSignInActivity(){

        startActivityForResult(

                AuthUI.getInstance()

                        .createSignInIntentBuilder()

                        .setTheme(R.style.LoginTheme)

                        .setAvailableProviders(

                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build()))

                        .setIsSmartLockEnabled(false, true)

                        .setLogo(R.drawable.family_icon)

                        .build(),

                RC_SIGN_IN);

    }
  /*  public void setUserNull(){
        this.logged_User=null;
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkLogin();
        // --- Handle SignIn Activity response on activity result ---
        this.handleResponseAfterSignIn(requestCode, resultCode, data);

    }

    //  - --Show Snack Bar with a message---
    private void showToast( String message){
        Toast.makeText(this, message, Snackbar.LENGTH_LONG).show();

    }
    // --------------------
    // UTILS
    // --------------------
    // --- Method that handles response after SignIn Activity close---

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) { // SUCCESS
                showToast( "Authentification success");
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object value=dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue();
                        if(value==null) {
                            User logged_User;
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            logged_User = new User(user.getDisplayName(), user.getUid(), user.getEmail(), null);
                            logged_User.pushUser_toDataBase();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error){
                        System.out.println("Tag Error");

                    }
                });
                /*if (logged_User==null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    logged_User = new User(user.getDisplayName(), user.getUid(), user.getEmail(), null);
                    logged_User.pushUser_toDataBase();*/
            } else { // ERRORS

                if (response == null) {
                    showToast( "Authentification failed");
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast("No Internet, please activate wifi");
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showToast("Unknown error");;

                }

            }

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main).getClass()==OptionFragment.class){
            bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
        }
        else if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main).getClass()==GroupList.class){
            bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        }
        else{
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }


    }

}
