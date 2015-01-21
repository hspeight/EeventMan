package com.example.hector.EventMan;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context, String[] foods) {
        super(context, R.layout.custom_row ,foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        View customView = buckysInflater.inflate(R.layout.custom_row, parent, false);

        String singleFoodItem = getItem(position);
        TextView buckysText = (TextView) customView.findViewById(R.id.textViewList);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.imageViewList);

        buckysText.setText(singleFoodItem);
        buckysText.setTypeface(null, Typeface.BOLD_ITALIC);
        buckysText.setTextColor(Color.MAGENTA);
        buckysImage.setImageResource(R.drawable.calendar4list);
        return customView;
    }
}