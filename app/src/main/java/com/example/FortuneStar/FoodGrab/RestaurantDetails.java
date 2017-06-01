package com.example.FortuneStar.FoodGrab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RestaurantDetails extends AppCompatActivity {

    private String TAG ="viewDatabase";

    // Firebase Database stuff
    private FirebaseDatabase FD;
    private DatabaseReference DR;

    // Firebase Storage stuff
    private StorageReference SR;

    private String restaurantID;
    private Restaurant restaurant;
    private TextView RNView,RAView,RCView,RDView;
    private ImageView RImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        RNView = (TextView)findViewById(R.id.RestaurantName);
        RAView = (TextView)findViewById(R.id.RestaurantAddress);
        RCView = (TextView)findViewById(R.id.RestaurantContact);
        RDView = (TextView)findViewById(R.id.RestaurantDescription);
        RImageView=(ImageView)findViewById(R.id.RestaurantImage);

        Bundle intentData = getIntent().getExtras();
        restaurant = intentData.getParcelable("Restaurant");
        //restaurantID = getIntent().getStringExtra("Restaurant");

        //declare the database reference object.
        FD=FirebaseDatabase.getInstance();
        DR=FD.getReferenceFromUrl("https://moblieassignment2.firebaseio.com/");

        // load the restaurant contact from Firebase database
        DatabaseReference contactDR = DR.child("RestaurantDetails").child(restaurant.getPlace_id()).child("phone");
        contactDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String contact=dataSnapshot.getValue(String.class);
                RCView.setText(contact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // load the restaurant address from Firebase database
        DatabaseReference addressDR = DR.child("RestaurantDetails").child(restaurant.getPlace_id()).child("address");
        addressDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address=dataSnapshot.getValue(String.class);
                RAView.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // load the restaurant name from Firebase database
        DatabaseReference nameDR = DR.child("RestaurantDetails").child(restaurant.getPlace_id()).child("name");
        nameDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue(String.class);
                RNView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // load the restaurant description from Firebase database
        DatabaseReference descriptionDR = DR.child("RestaurantDetails").child(restaurant.getPlace_id()).child("description");
        descriptionDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description=dataSnapshot.getValue(String.class);
                RDView.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // TODO: Add proper error handling so it does not break when the image is missing
        SR = FirebaseStorage.getInstance().getReferenceFromUrl("gs://moblieassignment2.appspot.com");
        StorageReference imageSR = SR.child("RestaurantImages/"+restaurant.getPlace_id()+"/"+restaurant.getPlace_id()+"_image1.jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        imageSR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                RImageView.setImageBitmap(image);

            }
        });
    }

    /**
     * Opens the map activity
     * @param view
     */
    public void OpenMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("Restaurant", restaurant);
        startActivity(intent);
    }


}
