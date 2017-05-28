package com.example.FortuneStar.FoodGrab.Manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Daniel on 28/05/2017.
 *
 * Will handle locations and gps. Using this class we can get the current location using various methods.
 * Permissions will also be handled here as different permissions are important.
 */

public class GPSManager implements LocationListener {

    private static final String LOG_TAG = "GPS_MANAGER";

    private LocationManager locationManager;
    private Activity activity;
    private PermissionManager permissionManager;
    private Location currentLocation = null;

    private boolean isGPSEnabled = false; // Flag for GPS status
    private boolean isNetworkEnabled = false; // Flag for network status
    private boolean canGetLocation = false; // Flag for GPS status

    private Location location; // Location
    private double latitude; // Latitude
    private double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public GPSManager(Context context) {
        // Give context to the ContextWrapper class (May not be needed because of getApplicationClass())
        activity = (Activity) context;

        // Setup permissionManager class
        permissionManager = new PermissionManager(Manifest.permission.ACCESS_FINE_LOCATION, "To locate user", this.activity);

        // Create the location manager to get the context location services
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check location permission and start gathering location data
        if (checkLocationPermission()) {
            Log.i(LOG_TAG, "Setting up location updates");
            currentLocation = getLocation();
            if(isNetworkEnabled) locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            if(isGPSEnabled) locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // TODO: Stop getting location updates once we have done request (Save power and data)
        }
        // TODO: Handle when user denies location permissions
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, "onLocationChanged");
        Log.i(LOG_TAG, "New Location: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(LOG_TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(LOG_TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(LOG_TAG, "onProviderDisabled");
    }

    /**
     * Checks the location permissions and returns a boolean
     * @return boolean
     */
    private boolean checkLocationPermission() {
        return permissionManager.checkPermission();
    }

    /**
     *  Finds current location using either Network or GPS depending on which is available.
     * @return Location object of current or last known Location
     */
    private Location getLocation() {
        Log.i(LOG_TAG, "Getting location()");
        if (checkLocationPermission()) {
            try {
                if (!isGPSEnabled && !isNetworkEnabled) {
                    // No network provider is enabled
                    Log.e(LOG_TAG, "No Location Providers are enabled");
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.i(LOG_TAG, "Network");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // If GPS enabled, get latitude/longitude using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d(LOG_TAG, "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return location;
        } else {
            return null;
        }
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }
}
