package fr.p4.mareu.api;

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
    public static Room[] rooms = new Room[]{new Room("Peach"), new Room("Mario"), new Room("Luigi"), new Room("Bowser"), new Room("Daisy"),
            new Room("Rosalina"), new Room("Wario"), new Room("Booster"), new Room("Kong"), new Room("Waluigi")};
    public static List<Employee> DUMMY_PARTICIPANTS1 = Arrays.asList(
            new Employee("maxime@lamzone.com"),
            new Employee("jeanine@lamzone.com"),
            new Employee("paul@lamzone.com"),
            new Employee("elodie@lamzone.com"),
            new Employee("françois@lamzone.com"),
            new Employee("aurélie@lamzone.com")
    );
    public static List<Employee> DUMMY_PARTICIPANTS2 = Arrays.asList(
            new Employee("jean@lamzone.com"),
            new Employee("sophie@lamzone.com"),
            new Employee("guillaume@lamzone.com"),
            new Employee("anette@lamzone.com"),
            new Employee("fabien@lamzone.com"),
            new Employee("cécile@lamzone.com")
    );
    public static List<Employee> DUMMY_PARTICIPANTS3 = Arrays.asList(
            new Employee("pascal@lamzone.com"),
            new Employee("aline@lamzone.com"),
            new Employee("alain@lamzone.com"),
            new Employee("laura@lamzone.com"),
            new Employee("patrique@lamzone.com"),
            new Employee("emilie@lamzone.com")
    );
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(rooms[0], -16524603, DUMMY_PARTICIPANTS1, "Réunion A", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd)),
            new Meeting(rooms[1], -16524603, DUMMY_PARTICIPANTS2, "Réunion B", new TimeRange(sCalendarTimeStart2, sCalendarTimeEnd2)),
            new Meeting(rooms[2], -16524603, DUMMY_PARTICIPANTS3, "Réunion C", new TimeRange(sCalendarTimeStart, sCalendarTimeEnd))
    );

    static {
        sCalendarTimeStart.set(2021, 8, 6, 10, 15);
        sCalendarTimeStart2.set(2021, 8, 6, 14, 15);
        sCalendarTimeEnd.set(2021, 8, 6, 11, 45);
        sCalendarTimeEnd2.set(2021, 8, 6, 16, 45);
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}

