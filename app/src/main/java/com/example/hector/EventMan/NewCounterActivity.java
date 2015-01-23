package com.example.hector.EventMan;

//import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewCounterActivity extends ActionBarActivity {

    //private TextView textUpDown;
    MyDBHandler dbHandler;
    //String evString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter);
        dbHandler = new MyDBHandler(this, null, null, 1);

        //String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        String evstring = dbHandler.getAllEvents();

        if (evstring.length() == 0) {
            evstring = "You have no events set up. Click the + button to create a new one.";
        }

        String[] foods = evstring.split(",");

        ListAdapter buckysAdapter = new CustomAdapter(this, foods);
        ListView buckysListView = (ListView) findViewById(R.id.buckysListView);
        buckysListView.setAdapter(buckysAdapter);

        buckysListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                           int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewCounterActivity.this);
                builder.setTitle("Delete Event?")
                        .setIcon(R.drawable.ic_launcher)
                        .setMessage("Click OK to delete the event\n" + "?" )
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String selected = ((TextView) view.findViewById(R.id.textViewRowID)).getText().toString();
                                boolean result = dbHandler.deleteEvent(selected);
                                if (result) {
                                    Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

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
}
