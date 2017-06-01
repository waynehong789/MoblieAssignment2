package com.example.FortuneStar.FoodGrab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private Restaurant displayRestaurant;

    //Firebase Database stuff
    private FirebaseDatabase FD;
    private DatabaseReference DR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle data = getIntent().getExtras();
        displayRestaurant = data.getParcelable("Restaurant");

        // Get menu data from database
        //declare the database reference object.
        FD= FirebaseDatabase.getInstance();
        DR=FD.getReferenceFromUrl("https://moblieassignment2.firebaseio.com/");

       /* DatabaseReference nameDR = DR.child("MENU").child(displayRestaurant.getPlace_id()).child("MName");
        nameDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        DatabaseReference menuDR = DR.child("MENU");
        menuDR.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MenuListItem item = dataSnapshot.getValue(MenuListItem.class);
                Log.i("MENU _ACTIVITY", dataSnapshot.getKey() + " has a name of " + item.getName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
