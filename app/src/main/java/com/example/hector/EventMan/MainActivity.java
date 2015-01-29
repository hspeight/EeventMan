package com.example.hector.EventMan;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends ExpandableListActivity
{
    MyDBHandler dbHandler;
    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        dbHandler = new MyDBHandler(this, null, null, 1);
        // Create Expandable List and set it's properties
        final ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expandableList.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });

        // Set the Items of Parent
        setGroupParents();
        // Set The Child Data
        //setChildData();

        // Create the Adapter
        EventAdapter adapter = new EventAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }

    // method to add parent Items
    public void setGroupParents()
    {
        ArrayList<String> child = new ArrayList<String>();
        String evstring = dbHandler.getEventIDs();
        String[] foods = evstring.split(":");

        int i;
        String evTit;
        String rowID;
        for (i=0; i<foods.length; i++) {
            Events myEvent = dbHandler.getMyEvent(foods[i]);
            //evTit = foods[i].replaceAll("[0-9]+::", ""); // removes the record ID
            //rowID = foods[i].replaceAll("::.*$", ""); // removes everything after the record ID
           // Events myEvent = dbHandler.myEvent(rowID);
            parentItems.add(myEvent.get_eventname());
            child.add(formatDateTime(myEvent.get_evtime(),myEvent.get_direction()));
            childItems.add(child);
            child = new ArrayList<String>(); // reset child list to empty
        }
    }

    public String formatDateTime(int eventTime,int direction){
        String[] d = new String[] {"up from","down to"};
        long millis = eventTime;
        millis *= 1000;
        DateTime dt = new DateTime(millis, DateTimeZone.forOffsetHours(0)); // needs to be a local date
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy HH:mm");

        return "Count " + d[direction] + " " + dtf.print(dt);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
