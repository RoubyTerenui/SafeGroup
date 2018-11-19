package com.project.safegroup;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.safegroup.GroupDetails.GroupDetailActivity;
import com.project.safegroup.GroupDetails.GroupListActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.safegroup.NotificationRecap;
import com.project.safegroup.ProblemQuad;
import com.project.safegroup.R;
import com.project.safegroup.SafeQuad;
import com.project.safegroup.SectionStatePageAdapter;
import com.project.safegroup.ThreeButtons;

import java.util.Arrays;

import dataBase.model.User;

public class MainActivity extends AppCompatActivity {
    private ThreeButtons mainFragment;
    private ProblemQuad problemQuad;
    private DangerQuad dangerQuad;
    private SafeQuad safeQuad;
    private GroupQuad groupQuad;
    private NotificationRecap notificationRecap;
    private GroupSelection groupSelection;
    //private SectionStatePageAdapter mSectionStatePageAdapter;
    private int localState;
    private int localStatePrecision;
    private int localGroup;
    // ---User connected to the app---
    private User logged_User;
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
                    Intent intent = new Intent(getBaseContext(), GroupListActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
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

    }


    public void checkLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            setContentView(R.layout.activity_main);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            this.configureAndShowMainFragment();
            if (logged_User==null) {
                logged_User = new User(user.getDisplayName(), user.getUid(), user.getEmail(), null, mDatabaseReference);
                logged_User.pushUser_toDataBase( user.getUid());
            }
        } else {
            // No user is signed in.
            startSignInActivity();
        }
    }
    private void configureAndShowMainFragment(){

        mainFragment = (ThreeButtons) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        problemQuad= new ProblemQuad();
        dangerQuad = new DangerQuad();
        safeQuad = new SafeQuad();
        groupQuad = new GroupQuad();
        notificationRecap = new NotificationRecap();
        groupSelection = new GroupSelection();
        if (mainFragment == null) {
            mainFragment = new ThreeButtons();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    public void setState(int state){ localState = state; }

    public void setStatePrecision(int statePresision){
        localStatePrecision=statePresision;
    }

    public void setGroup(int group){localGroup=group;}

    public void setLocalStateColor(){
        ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.container);
        int[] stateColor = getResources().getIntArray(R.array.state_color);
        rl.setBackgroundColor(stateColor[localState]);
    }

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
            default:
                break;
        }
    }


    public void setNotificationDetail(){
        Resources res = getResources();
        String[] state = res.getStringArray(R.array.state);
        String[] preciseState = res.getStringArray(R.array.precise_state);
        String[] group = res.getStringArray(R.array.group);
        String detail = String.format(res.getString(R.string.notification_detail_message),state[localState], preciseState[localStatePrecision],group[localGroup]);
        TextView detailText = (TextView) findViewById(R.id.detail_notification);
        detailText.setText(detail);
    }


    public void chooseGroup(){
        //TODO -- Choisir les groupes à partir de la base de donnée
    }

    // --- http Request for sign-out ---

    private void signOutUserFromFirebase(){

        AuthUI.getInstance().signOut(this);

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
                showToast( "authentification success");
            } else { // ERRORS

                if (response == null) {
                    showToast( "autentification failed");
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast("no Internet");
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showToast("Unknown error");;

                }

            }

        }

    }

}
