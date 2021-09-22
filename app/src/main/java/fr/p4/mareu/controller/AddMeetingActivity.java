package fr.p4.mareu.controller;

import static fr.p4.mareu.api.DummyMeetingGenerator.rooms;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;
import java.util.Calendar;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.R;
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
    private ArrayList<String> mRoomList;
    private ArrayList<Employee> mEmployeeList;
    private String mRoomChosen;
    private int mColor;
    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private Calendar currentCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUi();
    }

    private void initData() {
        mRoomList = new ArrayList<String>();
        mEmployeeList = new ArrayList<Employee>();
        mDate = Calendar.getInstance();
        mStart = Calendar.getInstance();
        mEnd = Calendar.getInstance();
        mColor = -16524603;
    }

    private void initUi() {
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.addMeetingEditTextDate.setOnClickListener(v -> dateDialog());
        mBinding.addMeetingEditTextTimeStart.setOnClickListener(this);
        mBinding.addMeetingEditTextTimeEnd.setOnClickListener(this);
        mBinding.buttonAddEmployees.setOnClickListener(this);
        mBinding.buttonResetEmployees.setOnClickListener(v -> resetEmployee());
        mBinding.buttonAddMeeting.setOnClickListener(v -> createMeeting());
        mBinding.addMeetingColorPickerBtn.setOnClickListener(v -> colorDialog());
        mBinding.addMeetingEditTextSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.outlinedTextFieldSubject.setErrorEnabled(false);
            }
        });
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
        if (view == mBinding.buttonAddEmployees && mBinding.addMeetingEditTextEmployeesList!=null){
            addEmployee();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    private void setLayoutColor(ColorEnvelope envelope) {
        mBinding.addMeetingColorPickerBtn.setBackgroundTintList(ColorStateList.valueOf(envelope.getColor()));
        mColor = envelope.getColor();
    }

    private void spinnerConfig() {
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mRoomList);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinnerRooms.setAdapter(dataAdapterR);
        mBinding.spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoomChosen = String.valueOf(mBinding.spinnerRooms.getSelectedItem());
                Toast.makeText(AddMeetingActivity.this, "OnClickListener : " + "\nSpinner : " + mRoomChosen, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void dateDialog() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "TAGD");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                currentCalendar = Calendar.getInstance();
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
                currentCalendar.set(Calendar.SECOND, 0);
                mDate.setTimeInMillis(selection);
                if (mDate.before(currentCalendar)) { // Error if past date chosen
                    mBinding.outlinedTextFieldDate.setErrorEnabled(true);
                    mBinding.outlinedTextFieldDate.setError("Invalid Date");
                    mBinding.addMeetingEditTextDate.setText(null);
                } else {
                    mBinding.outlinedTextFieldDate.setErrorEnabled(false); // Valid date chosen
                    mBinding.addMeetingEditTextDate.setText(materialDatePicker.getHeaderText());
                    mStart.set(Calendar.YEAR, mDate.get(Calendar.YEAR)); //update Calendar mStart & mEnd if new date chosen
                    mStart.set(Calendar.MONTH, mDate.get(Calendar.MONTH));
                    mStart.set(Calendar.DAY_OF_MONTH, mDate.get(Calendar.DAY_OF_MONTH));
                    mEnd.set(Calendar.YEAR, mDate.get(Calendar.YEAR));
                    mEnd.set(Calendar.MONTH, mDate.get(Calendar.MONTH));
                    mEnd.set(Calendar.DAY_OF_MONTH, mDate.get(Calendar.DAY_OF_MONTH));
                    if (!mBinding.addMeetingEditTextTimeEnd.getText().toString().isEmpty()) { //update spinner if hours are already specified
                        spinnerUpdate();
                    }
                }
            }
        });
    }

    private void timeDialog(int nbr) {
        MaterialTimePicker builder = new MaterialTimePicker.Builder()
                .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                .setMinute(Calendar.MINUTE)
                .build();
        builder.show(getSupportFragmentManager(), "TAGT");
        builder.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nbr == 0) {// Start time picker chosen
                    mStart.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), builder.getHour(), builder.getMinute());
                    mBinding.addMeetingEditTextTimeStart.setText(builder.getHour() + " : " + builder.getMinute());
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
                    mEnd.set(mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), builder.getHour(), builder.getMinute());
                    if (mEnd.before(mStart)) {
                        mBinding.addMeetingEditTextTimeEnd.setText(null);
                        mBinding.outlinedTextFieldTimeEnd.setError("Invalid End Time");
                    } else {
                        mBinding.addMeetingEditTextTimeEnd.setText(builder.getHour() + " : " + builder.getMinute());
                        mBinding.outlinedTextFieldTimeEnd.setErrorEnabled(false);
                        spinnerUpdate();
                    }
                }
            }
        });
    }

    private void colorDialog(){
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this)
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("Test")
                .setPositiveButton(getString(R.string.confirm), (ColorEnvelopeListener) (envelope, fromUser) -> setLayoutColor(envelope))
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
            builder.getColorPickerView().setFlagView(new BubbleFlag(this));
            builder.show();
    }

    private void roomChoice() {
        TimeRange meetingDuration = new TimeRange(mStart, mEnd);
        boolean onceIntersected;
        for (Room room : rooms
        ) {
            onceIntersected = false;
            //If no Room unavailability then add room to free room list
            if (room.getUnavailability().size() == 0) {
                mRoomList.add(room.getId());
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
                    mRoomList.add(room.getId());
                }
            }
        }
    }

    private void addEmployee(){
        char[] employees=mBinding.addMeetingEditTextEmployeesList.getText().toString().toCharArray();

        mBinding.addMeetingTextViewEmployeesList.setText(null);
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
        mApiService.createMeeting(new Meeting( findRoom(), mColor, mEmployeeList, mBinding.addMeetingEditTextSubject.getText().toString(), new TimeRange(mStart, mEnd)));

        onBackPressed();
    }

    private Room findRoom() {
        int b = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (mRoomChosen.contentEquals(rooms[i].getId())) {
                b = i;
            }
        }
        return rooms[b];
    }
}