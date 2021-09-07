package fr.p4.mareu.controller;


import static fr.p4.mareu.api.DummyMeetingGenerator.rooms;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import fr.p4.mareu.api.DummyMeetingApiService;
import fr.p4.mareu.databinding.ActivityAddMeetingBinding;
import fr.p4.mareu.model.Room;
import fr.p4.mareu.model.TimeRange;


public class AddMeetingActivity extends AppCompatActivity {

    private ActivityAddMeetingBinding mBinding;
    public Calendar mDate;
    private Calendar mStart;
    private Calendar mEnd;
    private ArrayList<String> mRoomList;
//    private DummyMeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUi();
    }

    private void initData() {
        mRoomList = new ArrayList<String>(0);
        mDate = Calendar.getInstance();
        mStart = Calendar.getInstance();
        mEnd = Calendar.getInstance();
    }

    private void initUi() {
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.addMeetingEditTextDate.setOnClickListener(v -> dateDialog());
        mBinding.addMeetingEditTextTimeStart.setOnClickListener(v -> timeDialog(0));
        mBinding.addMeetingEditTextTimeEnd.setOnClickListener(v -> timeDialog(1));

    }

    private void spinnerConfig() {
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mRoomList);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinnerRooms.setAdapter(dataAdapterR);

        mBinding.spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSpinner = String.valueOf(mBinding.spinnerRooms.getSelectedItem());
                Toast.makeText(AddMeetingActivity.this, "OnClickListener : " + "\nSpinner : " + itemSpinner, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });
    }

    private void dateDialog() {


        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mDate.set(i, i1, i2);
                mBinding.addMeetingEditTextDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
            }
        };

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        // Show
        datePickerDialog.show();
    }

    private void timeDialog(int nbr) {

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (nbr == 0) {
                    mStart.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), i, i1);
                    Log.i("timeDialog - mStart", "A : " + mStart.get(Calendar.YEAR) + "   M : " + mStart.get(Calendar.MONTH) + "   D : " + mStart.get(Calendar.DAY_OF_MONTH) + "   h : " + mStart.get(Calendar.HOUR_OF_DAY) + "   m : " + mStart.get(Calendar.MINUTE));
                    mBinding.addMeetingEditTextTimeStart.setText(i + " : " + i1);
                    //TODO controle de l'heure fin supérieur à heure début
                } else {
                    mRoomList.clear();
                    mEnd.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), i, i1);
                    Log.i("timeDialog - mEnd", "A : " + mEnd.get(Calendar.YEAR) + "   M : " + mEnd.get(Calendar.MONTH) + "   D : " + mEnd.get(Calendar.DAY_OF_MONTH) + "   h : " + mEnd.get(Calendar.HOUR_OF_DAY) + "   m : " + mEnd.get(Calendar.MINUTE));
                    mBinding.addMeetingEditTextTimeEnd.setText(i + " : " + i1);
                    roomChoice();
                    spinnerConfig();
                }
            }

        };
        // Create TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);

        // Show
        timePickerDialog.show();
    }


    private void roomChoice() {
        TimeRange meetingDuration = new TimeRange(mStart, mEnd);
        boolean onceIntersected;
        for (Room room : rooms
        ) {
            onceIntersected = false;
            //If no Room unavailability then add room to free room list
            if (room.getUnavailability().size() == 0) {
                mRoomList.add(String.valueOf(room.getNumber()));
            } else {
                // We browse room unavailability list to check if it intersect with meeting time slot
                for (TimeRange duration : room.getUnavailability()
                ) {
                    if (duration.intersect(meetingDuration) == true) {
                        onceIntersected = true;
                    }
                }
                //If no intersection happened while checking unavailability list then we add room to free rooms list (mRoomList)
                if (onceIntersected == false) {
                    mRoomList.add(String.valueOf(room.getNumber()));
                }
            }
        }
    }
}