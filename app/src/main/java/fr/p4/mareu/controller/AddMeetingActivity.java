package fr.p4.mareu.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.p4.mareu.R;
import fr.p4.mareu.databinding.ActivityAddMeetingBinding;
import fr.p4.mareu.databinding.ActivityMainBinding;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAddMeetingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();

    }


    private void initUi() {
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.addMeetingEditTextDate.setOnClickListener(this);
        mBinding.addMeetingEditTextTimeStart.setOnClickListener(this);

    }

    private void dateDialog() {
        Calendar calendar = Calendar.getInstance();
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mBinding.addMeetingEditTextDate.setText(i2+"/"+(i1+1)+"/"+i);
            }
        };

        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        // Show
        datePickerDialog.show();
    }

    private void timeDialog(){
        Calendar calendar = Calendar.getInstance();
        int selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
        int selectedMin = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mBinding.addMeetingEditTextTimeStart.setText(i+" : "+i1);
            }
        };
        // Create DatePickerDialog (Spinner Mode):
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, selectedHour, selectedMin, true);

        // Show
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view == mBinding.addMeetingEditTextDate){
            dateDialog();
        }
        if(view == mBinding.addMeetingEditTextTimeStart){
            timeDialog();
        }
    }
}