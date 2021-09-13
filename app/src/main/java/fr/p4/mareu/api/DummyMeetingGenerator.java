package fr.p4.mareu.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;
import fr.p4.mareu.model.TimeRange;


public abstract class DummyMeetingGenerator {


    public static Calendar sCalendarTimeStart = Calendar.getInstance();
    public static Calendar sCalendarTimeStart2 = Calendar.getInstance();
    public static Calendar sCalendarTimeEnd = Calendar.getInstance();
    public static Calendar sCalendarTimeEnd2 = Calendar.getInstance();


    public static Room[] rooms = new Room[]{new Room(25), new Room(5), new Room(29), new Room(32), new Room(45),
            new Room(12), new Room(49), new Room(17), new Room(37), new Room(3)};


    public static ArrayList<Employee> DUMMY_PARTICIPANTS1 = new ArrayList<>(Arrays.asList(
            new Employee("maxime@lamzone.com"),
            new Employee("jeanine@lamzone.com"),
            new Employee("paul@lamzone.com"),
            new Employee("elodie@lamzone.com"),
            new Employee("françois@lamzone.com"),
            new Employee("aurélie@lamzone.com")
    ));
    public static ArrayList<Employee> DUMMY_PARTICIPANTS2 = new ArrayList<>(Arrays.asList(
            new Employee("jean@lamzone.com"),
            new Employee("sophie@lamzone.com"),
            new Employee("guillaume@lamzone.com"),
            new Employee("anette@lamzone.com"),
            new Employee("fabien@lamzone.com"),
            new Employee("cécile@lamzone.com")
    ));
    public static ArrayList<Employee> DUMMY_PARTICIPANTS3 = new ArrayList<>(Arrays.asList(
            new Employee("pascal@lamzone.com"),
            new Employee("aline@lamzone.com"),
            new Employee("alain@lamzone.com"),
            new Employee("laura@lamzone.com"),
            new Employee("patrique@lamzone.com"),
            new Employee("emilie@lamzone.com")
    ));


    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(001, rooms[0], DUMMY_PARTICIPANTS1, "Peach", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd)),
            new Meeting(002, rooms[0], DUMMY_PARTICIPANTS2, "Luigi", new TimeRange(sCalendarTimeStart2, sCalendarTimeEnd2)),
            new Meeting(003, rooms[8], DUMMY_PARTICIPANTS3, "Mario", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd))
    );


    static List<Meeting> generateMeetings() {
        Log.i("TRACE1", String.valueOf("ici"));
        sCalendarTimeStart.set(2021, 8, 6, 10, 15);
        sCalendarTimeStart2.set(2021, 8, 6, 14, 15);
        sCalendarTimeEnd.set(2021, 8, 6, 11, 45);
        sCalendarTimeEnd2.set(2021, 8, 6, 16, 45);
        Log.i("TRACE 1.5", String.valueOf("là"));


        return new ArrayList<>(DUMMY_MEETINGS);
    }
}

