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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context, String[] foods) {
        super(context, R.layout.custom_row ,foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        View customView = buckysInflater.inflate(R.layout.custom_row, parent, false);

        String singleFoodItem = getItem(position);
        //System.out.println("!!- " + "*******************************************");

        //System.out.println("!!- " + singleFoodItem);
        //String singleID = getItem(position);
        TextView buckysText = (TextView) customView.findViewById(R.id.textViewList);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.imageViewList);
        TextView rowid = (TextView) customView.findViewById(R.id.textViewRowID);

        buckysText.setText(singleFoodItem.replaceAll("[0-9]+::",""));
        buckysText.setTypeface(null, Typeface.BOLD_ITALIC);
       // buckysText.setBackgroundColor(Color.parseColor("#636161"));
        buckysImage.setImageResource(R.drawable.calendar4list);
        rowid.setText(singleFoodItem.replaceAll("::.*$",""));
        //System.out.println("!!- " + "id is " + rowid.getText() + " string is " + buckysText.getText());
        return customView;
    }
}