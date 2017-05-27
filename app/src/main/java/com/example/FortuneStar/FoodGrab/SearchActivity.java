package com.example.FortuneStar.FoodGrab;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GAPIManager.GAPIResponseHandler, AdapterView.OnItemClickListener {

    private String TAG = "SearchActivity";
    private int REQUEST_PLACE_PICKER = 1;

    private GoogleApiClient mGoogleApiClient;
    private GAPIManager api;

    private ArrayList<Restaurant> restaurantsList;
    private ListView restaurantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        api = new GAPIManager();
        api.responseHandler = this; // Tell the response handler that we want to use this class for a callback

        restaurantsList = new ArrayList<Restaurant>();

        // Setup listener for list view
        restaurantListView = (ListView) findViewById(R.id.resultsList);
        restaurantListView.setOnItemClickListener(this);
    }

    public void findPlaces(View view){
        Log.i(TAG, "Finding Places");
        Location myLoc = new Location("");
        //-33.8670522,151.1957362 - Google's example coordinates
        myLoc.setLatitude(-36.846272);
        myLoc.setLongitude(174.768259);
        api.getLocalPlaces(myLoc, 500, "restaurant");

        // Construct an intent for the place picker
        /*try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, "Error Google Repairable");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, "Error Google Services Unavailable");
        }*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Error occurred connecting to google");
    }

    @Override
    public void onDataReturned(String response) {
        //Log.i(TAG, "Response received(onDataReturned()):" + response);
        try{
            // Parse the JSON into a restaurant class
            JSONObject json = new JSONObject(response);
            if(json.getString("status").equals("OK")){
                JSONArray results = json.getJSONArray("results");
                for(int i = 0; i < results.length(); i++){
                    // Get the json object from JSONArray
                    JSONObject jsonResult = results.getJSONObject(i);

                    // Build a restaurant object and add it to our results ArrayList
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(jsonResult.getString("id"));
                    restaurant.setName(jsonResult.getString("name"));
                    restaurant.setPlace_id(jsonResult.getString("place_id"));
                    JSONObject opening_hours = jsonResult.getJSONObject("opening_hours");
                    restaurant.setOpen_now(opening_hours.getBoolean("open_now"));
                    restaurant.setRating(jsonResult.getDouble("rating"));
                    restaurant.setVicinity(jsonResult.getString("vicinity"));
                    // TODO: Implement images for list and detail page

                    restaurantsList.add(restaurant);
                    //Log.i(TAG, "Added Restaurant to list");
                }
                Log.i(TAG, "Added all restaurants");

                ResultAdapter adbResult = new ResultAdapter(this, 0, restaurantsList);
                restaurantListView.setAdapter(adbResult);

            } else {
                Log.i(TAG, "Response Status is not OK: " + json.getString("status"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
