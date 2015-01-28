package com.example.hector.EventMan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ExpandableListView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends Activity {
    MyDBHandler dbHandler;
    EventAdapter myBaseExpandableListAdapter;
    ExpandableListView myExpandableListView;
    List<String> myListForGroup;
    HashMap<String, List<String>> myMapForChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        dbHandler = new MyDBHandler(this, null, null, 1);

        myExpandableListView = (ExpandableListView)
                findViewById(R.id.exp_list);

        initData();

        myBaseExpandableListAdapter = new
                EventAdapter(this, myListForGroup, myMapForChild);

        myExpandableListView.setAdapter(myBaseExpandableListAdapter);
    }

    private void initData() {
        myListForGroup = new ArrayList<String>();
        myMapForChild = new HashMap<String, List<String>>();

        //String listGroup[] = new String[] {"A","B","C","D","E","F","G","H","I","J"};

        String evstring = dbHandler.getAllEvents();
        String[] foods = evstring.split("~");
        //List<String> listGroupA;
        int i;
        String evTit;
        String rowID;
        for (i=0; i<foods.length; i++){
            evTit = foods[i].replaceAll("[0-9]+::",""); // removes the record ID
            rowID = foods[i].replaceAll("::.*$",""); // removes everything after the record ID
            Events myEvent = dbHandler.myEvent(rowID);

            setupExpandableListItems(evTit, i, myEvent.get_evtime(), myEvent.get_direction());
 /*
                System.out.println("!!- " + i);
                List<String> listGroupA = new ArrayList<String>();
                listGroupA.add("event detail");
                myListForGroup.add(foods[i]);
                myMapForChild.put(foods[i], listGroupA);
  */
           // }
           // myListForGroup.add(foods[i]);
        }
/*
        List<String> listGroupA = new ArrayList<String>();
        listGroupA.add(foods[0]);
        listGroupA.add(foods[1]);
        listGroupA.add(foods[2]);

        List<String> listGroupB = new ArrayList<String>();
        listGroupB.add("B - 1");

        List<String> listGroupC = new ArrayList<String>();
        listGroupC.add("C - 1");
        listGroupC.add("C - 2");
*/
        //myListForGroup.add("Group A");
      //  myListForGroup.add("Group B");
       // myListForGroup.add("Group C");

        //myMapForChild.put(myListForGroup.get(0), listGroupA);
      //  myMapForChild.put(myListForGroup.get(1), listGroupB);
      //  myMapForChild.put(myListForGroup.get(2), listGroupC);
    }

    public void setupExpandableListItems(String food, int i, int eventTime, int direction){

        //int i;
        //System.out.println("!!- " + i);
        switch (i) {
            case 0:
                List<String> listGroupA = new ArrayList<String>();
                listGroupA.add(formatDateTime(eventTime,direction));
                myMapForChild.put(food, listGroupA);
                break;
            case 1:
                List<String> listGroupB = new ArrayList<String>();
                listGroupB.add(formatDateTime(eventTime,direction));
                myMapForChild.put(food, listGroupB);
                break;
            case 2:
                List<String> listGroupC = new ArrayList<String>();
                listGroupC.add(formatDateTime(eventTime,direction));
                myMapForChild.put(food, listGroupC);
                break;
        }
        myListForGroup.add(food);
    }

    public String formatDateTime(int eventTime,int direction){
        String[] d = new String[] {"up from","down to"};
        long millis = eventTime;
        millis *= 1000;
        DateTime dt = new DateTime(millis, DateTimeZone.forOffsetHours(0)); // needs to be a local date
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy HH:mm");

        return "Count " + d[direction] + " " + dtf.print(dt);
    }
}