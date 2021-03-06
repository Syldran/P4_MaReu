package fr.p4.mareu.api;

import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;

public interface MeetingApiService {
    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

    List<Meeting> getMeetingsFilteredByDate(Calendar calendar);

    List<Meeting> getMeetingsFilteredByRoom(Room room);

}
