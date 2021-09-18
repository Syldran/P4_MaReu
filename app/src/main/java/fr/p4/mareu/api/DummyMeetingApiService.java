package fr.p4.mareu.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;

public class DummyMeetingApiService implements MeetingApiService{

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
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
    public List<Meeting> getMeetingsFilteredByDate(Calendar calendar) {
        List<Meeting> result = new ArrayList<>();

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
    public List<Meeting> getMeetingsFilteredByRoom(Room room) {
        List<Meeting> result = new ArrayList<>();

        for (int i = 0 ; i < mMeetings.size(); i++){
            boolean sameRoom = room == mMeetings.get(i).getRoom();
            if(sameRoom){
                result.add(mMeetings.get(i));
            }
        }
        return result;
    }
}
