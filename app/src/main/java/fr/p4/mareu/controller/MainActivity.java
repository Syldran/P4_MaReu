package fr.p4.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.api.DummyMeetingApiService;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.databinding.ActivityMainBinding;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding mBinding;
    private MeetingApiService mApiService = DI.getMeetingApiService();
    private ArrayList<Meeting> mMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetings= new ArrayList<>(mApiService.getMeetings());
        initUi();

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

        MeetingAdapter meetingAdapter = new MeetingAdapter(mMeetings);
        mBinding.mainMeetingRecyclerview.setAdapter(meetingAdapter);
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