package com.example.FortuneStar.FoodGrab.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.FortuneStar.FoodGrab.R;
import com.example.FortuneStar.FoodGrab.Restaurant;

import java.util.ArrayList;

/**
 * Created by Daniel on 27/05/2017.
 *
 *  Used for creating list object for displaying details in the adapter
 *
 */

public class ResultAdapter extends ArrayAdapter<Restaurant> {

    private Activity activity;
    private ArrayList<Restaurant> restaurantList;

    private static LayoutInflater inflater = null;

    public ResultAdapter (Activity activity, int textViewResourceId, ArrayList<Restaurant> _restaurantList) {
        super(activity, textViewResourceId, _restaurantList);
        try {
            this.activity = activity;
            this.restaurantList = _restaurantList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception ex) {
            Log.e("RESULTS_ADAPTER", "Error occurred trying to setup adapter for article list");
        }
    }

    public int getCount() { return restaurantList.size(); }

    public Restaurant getItem(int position){
        return restaurantList.get(position);
    }

    public long getItemId(int position) { return position; }

    // Used to hold information to be displayed
    public static class ViewHolder {
        public TextView display_name;
        public TextView display_vicinity;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if(convertView == null){
                vi = inflater.inflate(R.layout.result_list_item, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.name);
                holder.display_vicinity = (TextView) vi.findViewById(R.id.vicinity);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_name.setText(restaurantList.get(position).getName());
            holder.display_vicinity.setText(restaurantList.get(position).getVicinity());
        } catch (Exception ex) {

        }
        return vi;
    }

}
