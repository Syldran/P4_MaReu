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

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.databinding.ActivityAddMeetingBinding;
import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;
import fr.p4.mareu.model.TimeRange;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddMeetingBinding mBinding;
    public Calendar mDate;
    private Calendar mStart;
    private Calendar mEnd;
    private ArrayList<Integer> mRoomList;
    private ArrayList<Employee> mEmployeeList;
    private int mRoomChosen;
    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private Calendar currentCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUi();
    }

    private void initData() {
        mRoomList = new ArrayList<Integer>();
        mEmployeeList = new ArrayList<Employee>();
        mDate = Calendar.getInstance();
        mStart = Calendar.getInstance();
        mEnd = Calendar.getInstance();
    }

    private void initUi() {
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.addMeetingEditTextDate.setOnClickListener(v -> dateDialog());
        mBinding.addMeetingEditTextTimeStart.setOnClickListener(this);
        mBinding.addMeetingEditTextTimeEnd.setOnClickListener(this);
        mBinding.buttonAddEmployees.setOnClickListener(v -> addEmployee());
        mBinding.buttonResetEmployees.setOnClickListener(v -> resetEmployee());
        mBinding.buttonAddMeeting.setOnClickListener(v -> createMeeting());
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.addMeetingEditTextTimeStart && currentCalendar.before(mDate) && !mBinding.addMeetingEditTextDate.getText().toString().isEmpty()) {
            timeDialog(0);
        }
        if (view == mBinding.addMeetingEditTextTimeEnd) {
            if (!mBinding.addMeetingEditTextTimeStart.getText().toString().isEmpty())
                timeDialog(1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void spinnerConfig() {
        ArrayAdapter<Integer> dataAdapterR = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, mRoomList);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinnerRooms.setAdapter(dataAdapterR);
        mBinding.spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoomChosen = (int) mBinding.spinnerRooms.getSelectedItem();
                Toast.makeText(AddMeetingActivity.this, "OnClickListener : " + "\nSpinner : " + mRoomChosen, Toast.LENGTH_SHORT).show();
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
                currentCalendar = Calendar.getInstance();
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
                currentCalendar.set(Calendar.SECOND, 0);

                mDate.set(i, i1, i2);
                if (mDate.before(currentCalendar)) { // Error if past date chosen
                    mBinding.outlinedTextFieldDate.setErrorEnabled(true);
                    mBinding.outlinedTextFieldDate.setError("Invalid Date");
                    mBinding.addMeetingEditTextDate.setText(null);
                } else {
                    mBinding.outlinedTextFieldDate.setErrorEnabled(false); // Valid date chosen
                    mBinding.addMeetingEditTextDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    mStart.set(Calendar.YEAR, mDate.get(Calendar.YEAR)); //update Calendar mStart & mEnd if new date chosen
                    mStart.set(Calendar.MONTH, mDate.get(Calendar.MONTH));
                    mStart.set(Calendar.DAY_OF_MONTH, mDate.get(Calendar.DAY_OF_MONTH));
                    mEnd.set(Calendar.YEAR, mDate.get(Calendar.YEAR));
                    mEnd.set(Calendar.MONTH, mDate.get(Calendar.MONTH));
                    mEnd.set(Calendar.DAY_OF_MONTH, mDate.get(Calendar.DAY_OF_MONTH));
                    if(!mBinding.addMeetingEditTextTimeEnd.getText().toString().isEmpty()){ //update spinner if hours are already specified
                        spinnerUpdate();
                    }
                }
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
                if (nbr == 0) {// Start time picker chosen
                    mStart.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), i, i1);
                    mBinding.addMeetingEditTextTimeStart.setText(i + " : " + i1);
                    mBinding.outlinedTextFieldTimeStart.setErrorEnabled(false);
                    if (!mBinding.addMeetingEditTextTimeEnd.getText().toString().isEmpty()) { //End time already specified
                        if (mEnd.before(mStart)) { // if End time before Start time -> error
                            mBinding.addMeetingEditTextTimeEnd.setText(null);
                            mBinding.outlinedTextFieldTimeEnd.setError("Invalid End Time");
                            return;
                        }
                        spinnerUpdate();
                    }
                } else { //End time picker chosen
                    mEnd.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), i, i1);
                    if (mEnd.before(mStart)) {
                        mBinding.addMeetingEditTextTimeEnd.setText(null);
                        mBinding.outlinedTextFieldTimeEnd.setError("Invalid End Time");
                    } else {
                        mBinding.addMeetingEditTextTimeEnd.setText(i + " : " + i1);
                        mBinding.outlinedTextFieldTimeEnd.setErrorEnabled(false);
                        spinnerUpdate();
                    }
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
                mRoomList.add(room.getNumber());
            } else {
                // We browse room unavailability list to check if it intersect with meeting time slot
                for (TimeRange duration : room.getUnavailability()
                ) {
                    if (duration.intersect(meetingDuration)) {
                        onceIntersected = true;
                    }
                }
                //If no intersection happened while checking unavailability list then we add room to free rooms list (mRoomList)
                if (!onceIntersected) {
                    mRoomList.add(room.getNumber());
                }
            }
        }
    }

    private void addEmployee(){
        mBinding.addMeetingTextViewEmployeesList.setText(null);
        char[] employees=mBinding.addMeetingEditTextEmployeesList.getText().toString().toCharArray();

        if(employees.length==0) {
            mBinding.outlinedTextFieldEmployeesList.setError("Please type participants");
            return;
        }
        String name="";
        for (int i = 0 ; i < employees.length ;i++) {
            if (employees[i] != ' ' && employees[i]!=',' && employees[i]!='\n') {
                name += employees[i];
                Log.i("addEmployeeListNameC", name);
                if(i==employees.length-1){
                    mEmployeeList.add(new Employee(name));
                    name="";
                }
            } else {
                mEmployeeList.add(new Employee(name));
                name="";
            }
        }
        mBinding.addMeetingEditTextEmployeesList.setText(null);
        for (Employee e:mEmployeeList
             ) {
            mBinding.addMeetingTextViewEmployeesList.append(e.getMail()+" ");
        }
        mBinding.outlinedTextFieldEmployeesList.setErrorEnabled(false);
    }

    private void resetEmployee(){
        mEmployeeList.clear();
        mBinding.addMeetingTextViewEmployeesList.setText("");
    }

    private void spinnerUpdate(){
        mRoomList.clear();
        roomChoice();
        spinnerConfig();
    }

    private void createMeeting(){
        if (mBinding.addMeetingEditTextDate.getText().toString().isEmpty()){
            mBinding.outlinedTextFieldDate.setError("Please select a Date");
            return;
        }
        if(mBinding.addMeetingEditTextTimeStart.getText().toString().isEmpty()){
            mBinding.outlinedTextFieldTimeStart.setError("Please select starting time");
            return;
        }
        if(mBinding.addMeetingEditTextTimeEnd.getText().toString().isEmpty()){
            mBinding.outlinedTextFieldTimeEnd.setError("Please select ending time");
            return;
        }
        if (mBinding.addMeetingEditTextSubject.getText().toString().isEmpty()){
            mBinding.outlinedTextFieldSubject.setError("Please type your meeting subject");
            return;
        }
        if (mEmployeeList.isEmpty()){
            mBinding.outlinedTextFieldEmployeesList.setError("Please enter meeting participants");
            return;
        }
        if(mApiService.getMeetings().size()==0){
            mApiService.createMeeting(new Meeting(0, new Room(mRoomChosen),mEmployeeList,mBinding.addMeetingEditTextSubject.getText().toString(),new TimeRange(mStart,mEnd)));
        } else {
            mApiService.createMeeting(new Meeting(mApiService.getMeetings().get(mApiService.getMeetings().size()-1).getId()+1, findRoom(),mEmployeeList,mBinding.addMeetingEditTextSubject.getText().toString(),new TimeRange(mStart,mEnd)));
        }
        onBackPressed();
    }

    private Room findRoom() {
        int b = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (mRoomChosen == rooms[i].getNumber()) {
                b = i;
            }
        }
        return rooms[b];
    }
}