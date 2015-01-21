package com.example.hector.EventMan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventEditor extends ActionBarActivity implements OnClickListener {

    private TextView pDisplayDate;
    private TextView textTime;
    private TextView textUpDown;
    private Button addButton;
    private int pYear;
    private int pMonth;
    private int pDay;
    private int mHour;
    private int mMinute;
    private int idx;
    private int cidx;
    private int timeInSeconds;


    //public String countDirection = "Up";
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            updateTime();
        }
    };

    EditText hsEditText;

    MyDBHandler dbHandler;

    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;

                    updateDisplay();
                }
            };

    /** Updates the date in the TextView */
    private void updateDisplay() {

        addButton.setEnabled(true);

        //  boolean invalidDate = false;
        final DateTime dtNow = new DateTime();
        // System.out.println("!!- " + dtNow.getMinuteOfDay() + "/" + dtNow.getMinuteOfHour());

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioDirection);
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);
        idx = rg.indexOfChild(radioButton);

        if (idx == 1) {
            textUpDown.setText("Target Date");
        } else {
            textUpDown.setText("Start Date");
        }

        pDisplayDate.setTextColor(Color.BLUE);
        final DateTime dt = new DateTime(pYear, pMonth + 1, pDay, 0, 0);
        String month = dt.monthOfYear().getAsShortText();
        pDisplayDate.setText(pDay + " " + month  + " " + pYear);

        int valDate = validateInDate(dt.toString());

        if (idx == 0 && valDate == 1) { // Count up selected but date is in future
            pDisplayDate.setTextColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Future date not allowed", Toast.LENGTH_SHORT).show();
            addButton.setEnabled(false);
        } else {
            if (idx == 1 && valDate != 1) { // Count down selected but date is not in future
                pDisplayDate.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "This must be a future date", Toast.LENGTH_SHORT).show();
                addButton.setEnabled(false);
            }
        }

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxTime);
        if (checkBox.isChecked()) {
            cidx = 1;
            textTime.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new TimePickerDialog(EventEditor.this,
                            t,
                            dtNow.getHourOfDay(),
                            dtNow.getMinuteOfHour(),
                            true).show();
                    //updateTime();
                }
                //    Toast.makeText(getApplicationContext(), "Time field clicked", Toast.LENGTH_SHORT).show();
            });
            //textTime.setEnabled(false);
            //textTime.setText("01:00");
            //textTime.setText(new StringBuilder().append(pad(mHour)).append(":")
            textTime.setText(pad(dtNow.getHourOfDay()) + ":" + pad(dtNow.getMinuteOfHour()));

        } else {
            //System.out.println("!!- " + "box not checked");
            //textTime.setEnabled(true);
            cidx = 0;
            textTime.setText("");
        }

    }

    // updates the time we display in the TextView
    private void updateTime() {
        textTime.setText(
                new StringBuilder()
                        //       .append(pad(mHour)).append(":")
                        //      .append(pad(mMinute)));
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private int validateInDate(String inDate) {

        DateTime cd = new DateTime(); //current date
        return(DateTimeComparator.getDateOnlyInstance().compare(inDate, cd));

        //return result;
    }

    /** Displays a notification when the date is updated */
    private void displayToast() {
        // Toast.makeText(this, new StringBuilder().append("Date choosen is ").append(pDisplayDate.getText()), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_editor);

        hsEditText = (EditText) findViewById(R.id.hsEditText);
        // hsTextView = (TextView) findViewById(R.id.hsTextView);
        dbHandler = new MyDBHandler(this, null, null, 1);

        /** Capture our View elements */
        pDisplayDate = (TextView) findViewById(R.id.inputDate);
        textTime = (TextView) findViewById(R.id.textViewTime);
        textUpDown = (TextView) findViewById(R.id.textViewDirection);
        addButton = (Button) findViewById(R.id.buttonAdd);

        // pPickDate = (Button) findViewById(R.id.pickDate);

        /** Listener for click event of the date field */
        pDisplayDate.setOnClickListener(new View.OnClickListener() {
            //pPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
/*

        textTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(EventEditor.this,
                        t,
                        10,
                        11,
                        true).show();
            }
            //    Toast.makeText(getApplicationContext(), "Time field clicked", Toast.LENGTH_SHORT).show();
        });
*/
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioDirection);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                updateDisplay();
            }
        });

        CheckBox chkTime = (CheckBox) findViewById(R.id.checkBoxTime);
        chkTime.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View v) {
                updateDisplay();
            }
        });
        /** Display the current date in the TextView */
        updateDisplay();
    }

    public void addButtonClicked(View view) {

        String EventTitle = hsEditText.getText().toString();
        //System.out.println("!!- " + EventTitle + "/" + EventTitle.length());
        if(EventTitle.isEmpty()) {
            Toast mytoast = Toast.makeText(getApplicationContext(),"Please  title your event",Toast.LENGTH_SHORT);
            //        Toast.makeText(getApplicationContext(), "Please  title your event", Toast.LENGTH_SHORT).show();
            mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            mytoast.show();
            return;
        }

        String dbTime = "00:00";
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxTime);
        if (checkBox.isChecked()) {
            dbTime = (String) textTime.getText(); // Use time entered by user
        }

        String givenDateString = pDisplayDate.getText() + " " + dbTime; //e.g. 7 Jul 2014 15:30
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
        try {
            Date mDate = sdf.parse(givenDateString);
            //long timeInMilliseconds = mDate.getTime();
            timeInSeconds = (int)(mDate.getTime() / 1000);
            //System.out.println("!!- " + givenDateString + "/" + mDate.getTime() + "/" + (int)mDate.getTime() + "/" + timeInMilliseconds);
        } catch (ParseException e) {
            //System.out.println("!!- " + pDisplayDate.getText() + dbTime + "bad!");
            e.printStackTrace();
        }
        Events event = new Events(EventTitle, idx, timeInSeconds, cidx);
        dbHandler.addEvent(event);

        Toast.makeText(getApplicationContext(), "Your event has been created", Toast.LENGTH_SHORT).show();
        // printDatabase();
    }
    /*
        public void deleteButtonClicked(View view) {
            String inputText = hsEditText.getText().toString();
            dbHandler.deleteEvent(inputText);
            //printDatabase();
        }
    /*
        public void printDatabase() {
            String dbString = dbHandler.dbtostring();
        //    hsTextView.setText(dbString);
        //    hsEditText.setText("");
        }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_editor, menu);
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
    @Override
    public void onClick(View view) {

    }
    /** Create a new dialog for date picker */


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

}
