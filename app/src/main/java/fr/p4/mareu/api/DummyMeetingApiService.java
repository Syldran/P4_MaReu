package fr.p4.mareu.api;

import java.util.ArrayList;
import java.util.List;

import fr.p4.mareu.model.Meeting;

public class DummyMeetingApiService implements MeetingApiService{

    private ArrayList<Meeting> mMeetings = (ArrayList<Meeting>) DummyMeetingGenerator.generateMeetings();

    @Override
    public ArrayList<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }
}
