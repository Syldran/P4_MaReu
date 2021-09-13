package fr.p4.mareu.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;

public class DummyMeetingApiService implements MeetingApiService{

    private ArrayList<Meeting> mMeetings = (ArrayList<Meeting>) DummyMeetingGenerator.generateMeetings();

    @Override
    public ArrayList<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meeting.getRoom().removeUnavailability(meeting.getDuration());
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public ArrayList<Meeting> getMeetingsFilteredByDate(Calendar calendar) {
        ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(calendar.getTime());
        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(mMeetings.get(i).getDuration().getStart().getTime());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(mMeetings.get(i));

        }
        return result;
    }

    @Override
    public ArrayList<Meeting> getMeetingsFilteredByRoom(Room room) {
        ArrayList<Meeting> result = new ArrayList<>();

        for (int i = 0 ; i < mMeetings.size(); i++){
            boolean sameRoom = room == mMeetings.get(i).getRoom();
            if(sameRoom){
                result.add(mMeetings.get(i));
            }
        }
        return result;
    }
}
