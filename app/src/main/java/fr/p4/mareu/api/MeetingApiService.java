package fr.p4.mareu.api;

import java.util.List;

import fr.p4.mareu.model.Meeting;

public interface MeetingApiService {
    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);
}
