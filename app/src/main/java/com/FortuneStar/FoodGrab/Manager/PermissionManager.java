package com.FortuneStar.FoodGrab.Manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Daniel on 17/03/2017.
 *
 * Permissions utility class can check whether a permission is already granted and if not it can handle
 * requesting permission from the user.
 */

public class PermissionManager implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String LOG_TAG = "PermissionUtil";

    private String requestedPermission; // All permissions are string based of manifest object
    private String reason;
    private Activity activity;

    private static final int PERMISSION_REQUEST_RESULT = 986;

    // Will be true if the permission has been requested already
    public boolean hasPermission = false;


    public PermissionManager(String perm, String reason, Context context){
        requestedPermission = perm;
        this.reason = reason;
        activity = (Activity) context;

        if(checkPermission()){
            hasPermission = true;
        } else {
            hasPermission = false;
            requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_REQUEST_RESULT:
                Log.i(LOG_TAG, "Permission has been requested for the specified permission");
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i(LOG_TAG, "Permission was granted for the requested result");
                } else {
                    Log.i(LOG_TAG, "Permission was denied by user for the requested result");
                }
            default:
                Log.i(LOG_TAG, "Unknown permission requested");
        }
    }

    /**
     * Call if user does not have the requested permission. This function will display the permission field
     */
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, requestedPermission)){ // POSSIBLE ERROR!!!!!! CHECK ONCE RUNNING!!
            // Give and explanation of why we need this permission
            Log.i(LOG_TAG, "We need to explain rationale to user!");
            ActivityCompat.requestPermissions(activity, new String[]{ requestedPermission }, PERMISSION_REQUEST_RESULT);
        } else {
            // Request permission form user
            Log.i(LOG_TAG, "Requesting permission from user");
            ActivityCompat.requestPermissions(activity, new String[]{ requestedPermission }, PERMISSION_REQUEST_RESULT);
        }
    }

    /**
     * Check status of permission you are requesting
     * @return true if user has granted app specified permission
     */
    boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.i(LOG_TAG, "Permission Granted!");
            return true;
        } else {
            Log.i(LOG_TAG, "Permission Denied!");
            return false;
        }
    }
}
