package fr.p4.mareu.DI;


import fr.p4.mareu.api.DummyMeetingApiService;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.model.Meeting;

public class DI {
    private static final MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
