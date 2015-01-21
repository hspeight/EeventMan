package com.example.hector.EventMan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class NewCounterActivity extends ActionBarActivity {

    MyDBHandler dbHandler;
    String evString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter);
        dbHandler = new MyDBHandler(this, null, null, 1);
        //retrieveEvents();

        //String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        String evstring = dbHandler.getAllEvents();
        if (evstring.length() == 0) {
            evstring = "You have no events set up. Click the + button to create a new one.";

        }
        //System.out.println("!!- "  + evstring.length());
        String[] foods = evstring.split(",");
        ListAdapter buckysAdapter = new CustomAdapter(this, foods);
        ListView buckysListView = (ListView) findViewById(R.id.buckysListView);
        buckysListView.setAdapter(buckysAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, EventEditor.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public void retrieveEvents() {
        //Events event = new Events(EventTitle, idx, timeInMilliseconds, cidx);

        String dbString = dbHandler.getAllEvents();
        //dbHandler.getAllEvents();
        System.out.println("!!-  " + dbString);
    }
*/
}
