package com.FortuneStar.FoodGrab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.FortuneStar.FoodGrab.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

/**
 * Created by Wayne on 25/05/2017
 */

public class MainActivity extends AppCompatActivity  {
    private static final int RC_SIGN_IN = 0;

    private FirebaseAuth firebaseAuth;

    public static String userName;
    public static Uri userPhoto;
    public static String userID;
    public static String userEmailAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        isSignedIn();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(this, "Login Successful, Welcome " + firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                getUserDetails();
                // Start the search activity after logging in
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // user press back button
                    Toast.makeText(this, "Login Canceled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    /*@Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_out_button) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }

    }*/

    //check if user is signed in or not.
    private void isSignedIn() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Welcome Back, " + firebaseAuth.getCurrentUser().getDisplayName() + " !", Toast.LENGTH_SHORT).show();
            getUserDetails();
            // Start the search activity after logging in
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
        } else {
            //not signed in
            startActivityForResult(AuthUI
                            .getInstance()
                            .createSignInIntentBuilder()
                            //ToDo enable smart lock after development change false to (!BuildConfig.DEBUG)
                            .setIsSmartLockEnabled(false)
                            //ToDo add the majestic App logo
                            .setLogo(R.drawable.foodgrab)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                            //ToDo add Facebook later
                            .build(),
                    RC_SIGN_IN);
        }
    }

    public void getUserDetails()
    {
        //For easy access just call these variables in other activities.
        //user ID
        userID = firebaseAuth.getCurrentUser().getUid();
        //user name
        userName = firebaseAuth.getCurrentUser().getDisplayName();
        //user email address
        userEmailAddress = firebaseAuth.getCurrentUser().getEmail();
        //user photo URL
        userPhoto = firebaseAuth.getCurrentUser().getPhotoUrl();
    }

}
