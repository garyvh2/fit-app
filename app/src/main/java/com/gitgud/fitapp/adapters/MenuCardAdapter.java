package com.gitgud.fitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.components.MenuCardItem;
import com.gitgud.fitapp.ui.dashboard.dashboard.view.DashboardFragmentDirections;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MenuCardAdapter extends ArrayAdapter<MenuCardItem> {
    public MenuCardAdapter(Context context, ArrayList<MenuCardItem> cardItems) {
        super(context, 0, cardItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MenuCardItem cardItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_card_component, parent, false);
        }

        // Lookup view for data population
        ImageView icon = (ImageView) convertView.findViewById(R.id.card_menu_icon);
        TextView title = (TextView) convertView.findViewById(R.id.menu_card_title);
        MaterialCardView card = convertView.findViewById(R.id.menu_card_container);
        card.setTag(position);
        card.setOnClickListener(this::onClickCard);

        // Populate the data into the template view using the data object
        icon.setImageResource(cardItem.getIcon());
        title.setText(cardItem.getTitle());
        // Return the completed view to render on screen
        return convertView;
    }

   public void onClickCard (View v) {
       int position = (Integer) v.getTag();
       MenuCardItem item = getItem(position);
       if (item.getIsView()) {
           Intent intent = new Intent(v.getContext(), item.getView());
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           v.getContext().getApplicationContext().startActivity(intent);
       } else {
           Navigation.findNavController(v).navigate(item.getFragment());
       }

   }
}