package com.project.safegroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import butterknife.BindView;


public class AuthentificationUi extends AppCompatActivity {
    // 1 - Identifier for Sign-In Activity

    private static final int RC_SIGN_IN = 123;

    // --------------------
    // ACTIONS
    // --------------------
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    public int getFragmentLayout(){return R.layout.activity_login ;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void attemptLogin() {

        // 3 - Launch Sign-In Activity when user clicked on Login Button

        this.startSignInActivity();

    }

    // --------------------

    // NAVIGATION

    // --------------------


    // 2 - Launch Sign-In Activity

    private void startSignInActivity(){

        startActivityForResult(

                AuthUI.getInstance()

                        .createSignInIntentBuilder()

                        .setTheme(R.style.LoginTheme)

                        .setAvailableProviders(

                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))

                        .setIsSmartLockEnabled(false, true)

                        .setLogo(R.drawable.family_icon)

                        .build(),

                RC_SIGN_IN);

    }



    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            // 4 - Handle SignIn Activity response on activity result
            this.handleResponseAfterSignIn(requestCode, resultCode, data);
        }

    //  - Show Snack Bar with a message
    private void showToast( String message){
        Toast.makeText(this, message, Snackbar.LENGTH_LONG).show();

    }

    // --------------------

    // UTILS

    // --------------------


    // 3 - Method that handles response after SignIn Activity close

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){


        IdpResponse response = IdpResponse.fromResultIntent(data);


        if (requestCode == RC_SIGN_IN) {

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
