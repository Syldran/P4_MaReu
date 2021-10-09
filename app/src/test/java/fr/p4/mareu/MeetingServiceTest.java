package fr.p4.mareu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static fr.p4.mareu.api.DummyMeetingGenerator.DUMMY_PARTICIPANTS1;
import static fr.p4.mareu.api.DummyMeetingGenerator.rooms;
import static fr.p4.mareu.api.DummyMeetingGenerator.sCalendarDate;
import static fr.p4.mareu.api.DummyMeetingGenerator.sCalendarTimeEnd;
import static fr.p4.mareu.api.DummyMeetingGenerator.sCalendarTimeStart;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.api.DummyMeetingGenerator;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;
import fr.p4.mareu.model.TimeRange;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        //Check that getMeetings() really return our data from DUMMY_NEIGHBOURS
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertTrue(meetings.containsAll(expectedMeetings));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        //Check the neighbour isn't contained anymore after being deleted
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        //Check the neighbour is contained after being added
        Meeting meetingToAdd = new Meeting(rooms[0], -16524603, sCalendarDate, DUMMY_PARTICIPANTS1, "Réunion A", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd));
        service.createMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void dateFilteredMeeting() {
        Calendar dateFilter=Calendar.getInstance();
        dateFilter.set(2021,8,6);
        for (Meeting m : service.getMeetingsFilteredByDate(dateFilter)) {
            assertTrue(m.getDate().get(Calendar.DATE) == dateFilter.get(Calendar.DATE));
        }
    }

    @Test
    public void roomFilteredMeeting() {
        Meeting meeting = new Meeting(rooms[0], -16524603, sCalendarDate, DUMMY_PARTICIPANTS1, "Réunion A", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd));
        for (Meeting m : service.getMeetingsFilteredByRoom(rooms[5])) {
           assertTrue( m.getRoom().equals(meeting.getRoom()));
        }
    }

}