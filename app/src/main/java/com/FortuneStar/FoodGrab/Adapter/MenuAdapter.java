package com.FortuneStar.FoodGrab.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.FortuneStar.FoodGrab.MenuListItem;
import com.FortuneStar.FoodGrab.R;

import java.util.ArrayList;

/**
 * Created by Daniel on 31/05/2017.
 *
 * Used to help display the menu list items.
 */

public class MenuAdapter extends ArrayAdapter<MenuListItem> { // extends ArrayAdapter<Restaurant>
    private Activity activity;
    private ArrayList<MenuListItem> menuList;

    private static LayoutInflater inflater = null;

    public MenuAdapter (Activity activity, int textViewResourceId, ArrayList<MenuListItem> menuListItems) {
        super(activity, textViewResourceId, menuListItems);
        try {
            this.activity = activity;
            this.menuList = menuListItems;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception ex) {
            Log.e("MENULISTITEM_ADAPTER", "Error occurred trying to setup adapter for article list");
        }
    }

    public int getCount() { return menuList.size(); }

    public MenuListItem getItem(int position){
        return menuList.get(position);
    }

    public long getItemId(int position) { return position; }

    // Used to hold information to be displayed
    public static class ViewHolder {
        public TextView display_name;
        public TextView display_price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final MenuAdapter.ViewHolder holder;
        try {
            if(convertView == null){
                vi = inflater.inflate(R.layout.menu_list_item, null);
                holder = new MenuAdapter.ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.name);
                holder.display_price = (TextView) vi.findViewById(R.id.price);

                vi.setTag(holder);
            } else {
                holder = (MenuAdapter.ViewHolder) vi.getTag();
            }

            holder.display_name.setText(menuList.get(position).getName());
            holder.display_price.setText("$" + menuList.get(position).getPrice());
        } catch (Exception ex) {

        }
        return vi;
    }
}
