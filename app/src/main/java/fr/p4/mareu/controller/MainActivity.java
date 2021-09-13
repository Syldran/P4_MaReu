package fr.p4.mareu.controller;

import static fr.p4.mareu.api.DummyMeetingGenerator.rooms;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.R;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.databinding.ActivityMainBinding;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.controller.*;
import fr.p4.mareu.model.Room;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;
    private MeetingApiService mApiService = DI.getMeetingApiService();
    private List<Meeting> mMeetings;
    private MeetingAdapter meetingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetings= new ArrayList<>(mApiService.getMeetings());
        initUi();

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFilter();
    }

    private void initUi() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        setButton();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.mainMeetingRecyclerview.setLayoutManager(layoutManager);

        meetingAdapter = new MeetingAdapter(mMeetings, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.mainMeetingRecyclerview.getContext(),
                layoutManager.getOrientation());
        mBinding.mainMeetingRecyclerview.addItemDecoration(dividerItemDecoration);
        mBinding.mainMeetingRecyclerview.setAdapter(meetingAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter_date:
                dateDialog();
                return true;
            case R.id.filter_room:
                roomDialog();
                return true;
            case R.id.filter_reset:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dateDialog() {
// Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsFilteredByDate(cal));
                mBinding.mainMeetingRecyclerview.getAdapter().notifyDataSetChanged();
            }

        };

// Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

// Show
        datePickerDialog.show();
    }

    private void roomDialog(){
        String[] rooms1= new String[]{String.valueOf(rooms[0].getNumber()),String.valueOf(rooms[1].getNumber()),String.valueOf(rooms[2].getNumber()),String.valueOf(rooms[3].getNumber()),String.valueOf(rooms[4].getNumber()),String.valueOf(rooms[5].getNumber()),String.valueOf(rooms[6].getNumber()),String.valueOf(rooms[7].getNumber()),String.valueOf(rooms[8].getNumber()),String.valueOf(rooms[9].getNumber())};
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Rooms");
        dialog.setItems(rooms1, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
            switch (i){
                case 0 :
                    mMeetings.clear();
                    mMeetings.addAll(mApiService.getMeetingsFilteredByRoom(rooms[0]));
                    mBinding.mainMeetingRecyclerview.getAdapter().notifyDataSetChanged();
                    return;
            }
        });
        dialog.show();
    }

    private void resetFilter() {
        mMeetings.clear();
        mMeetings.addAll(mApiService.getMeetings());
        mBinding.mainMeetingRecyclerview.getAdapter().notifyDataSetChanged();
    }

    private void setButton() {
        mBinding.mainAddMeeting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mBinding.mainAddMeeting){
         startActivity(new Intent(this, AddMeetingActivity.class));
        }
    }


}