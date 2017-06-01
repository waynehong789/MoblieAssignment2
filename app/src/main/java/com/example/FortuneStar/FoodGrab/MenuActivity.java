package com.example.FortuneStar.FoodGrab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.FortuneStar.FoodGrab.Adapter.MenuAdapter;
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

    private ArrayList<MenuListItem> menuListItems;

    private ListView menuListView;
    private TextView menuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle data = getIntent().getExtras();
        displayRestaurant = data.getParcelable("Restaurant");

        menuListView = (ListView) findViewById(R.id.menuListView);

        menuListItems = new ArrayList<>();

        menuName = (TextView) findViewById(R.id.txtViewMenuName);
        menuName.setText(displayRestaurant.getName());

        // Get menu data from database
        //declare the database reference object.
        FD= FirebaseDatabase.getInstance();
        DR=FD.getReferenceFromUrl("https://moblieassignment2.firebaseio.com/");

        DatabaseReference menuDR = DR.child("MENU");
        menuDR.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Get children and iterate over
                Iterable<DataSnapshot> iDataSnapshot = dataSnapshot.getChildren();

                int menuArrayCount = 0;

                for(DataSnapshot ds : iDataSnapshot) {
                    if(dataSnapshot.getKey().equals(displayRestaurant.getPlace_id())) {
                        ds.child(menuArrayCount + "");
                        MenuListItem item = ds.getValue(MenuListItem.class);
                        menuListItems.add(item);
                        Log.i("MENU _ACTIVITY", dataSnapshot.getKey() + " has a name of " + item.getName());
                    }
                    menuArrayCount++;
                    Log.i("MENU_ACTIVITY", "Checked a datasnapshot key: " + dataSnapshot.getKey());
                }

                populateMenuList();
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

    public void populateMenuList(){
        //ResultAdapter adbResult = new ResultAdapter(this, 0, restaurantsList);
        //restaurantListView.setAdapter(adbResult);
        MenuAdapter adbMenu = new MenuAdapter(this, 0, menuListItems);
        menuListView.setAdapter(adbMenu);
    }
}
