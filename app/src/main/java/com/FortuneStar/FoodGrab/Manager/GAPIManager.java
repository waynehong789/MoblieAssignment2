package com.FortuneStar.FoodGrab.Manager;

import android.location.Location;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by Daniel on 27/05/2017.
 *
 * This class is used to send HTTP requests to the google places api this class will then return json
 */

public class GAPIManager {

    private final String TAG = "GAPI_MANAGER";

    private final String API_KEY = "AIzaSyAdSzh6Lhdd43nBv1yO37vY3IJxJDVA-AY";

    // Request queue for http requests we need to make
    private RequestQueue reqQueue;

    // Create a cache for saving some of our request data
    private Cache cache;

    // Set up the network for making the requests on
    private Network network;

    // Callback to run when we get a response from Google
    public GAPIResponseHandler responseHandler;

    public GAPIManager(){
        // setup network and cache
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        network = new BasicNetwork(new HurlStack());
        // Setup the request queue
        reqQueue = new RequestQueue(cache, network);
        reqQueue.start();
    }

    /**
     *  Sends a request to the Google Places API to get all places that meet the criteria of the
     *  parameters.
     * @param location A coordinate (Latitude and Longitude) of the location we want to search
     * @param radius The search radius in Metres for an area we want to search
     * @param type The type of place we would like to search. See https://developers.google.com/places/android-api/supported_types
     */
    public void getLocalPlaces(Location location, double radius, String type){
        //GET https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyAdSzh6Lhdd43nBv1yO37vY3IJxJDVA-AY&type=restaurant&location=-36.85749657,174.652164058&radius=500
        if(location != null) {
            String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
            String locationCoords = location.getLatitude() + "," + location.getLongitude();
            String requestStr = "key=" + API_KEY + "&type=" + type + "&location=" + locationCoords + "&radius=" + radius;

            String request = apiUrl + requestStr;

            // Start constructing the api http request
            StringRequest strRequest = new StringRequest(Request.Method.GET, request, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Do something with response
                    Log.i(TAG, "Received Response: " + response);
                    responseHandler.onDataReturned(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Log.e(TAG, "Received Error: " + error.toString());
                }
            });

            reqQueue.add(strRequest);
        }
    }

    /**
     * Callback to be called when we get a response from google. Implement this interface in your activity
     * then the function will be called once we get a response.
     */
    public interface GAPIResponseHandler {
        void onDataReturned(String data);
    }
}
