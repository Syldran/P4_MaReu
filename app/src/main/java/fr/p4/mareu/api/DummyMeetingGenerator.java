package fr.p4.mareu.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.model.Room;

public abstract class DummyMeetingGenerator {

    public static Room[] rooms=new Room[]{new Room(25),new Room(5),new Room(3),new Room(32),new Room(45),
            new Room(12),new Room(49),new Room(17),new Room(37),new Room(3)};



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
            new Meeting(rooms[0], DUMMY_PARTICIPANTS1, new Date(2021,11,2,10,30), new Date(2021,11,2,12,0), "Peach"),
            new Meeting(rooms[4], DUMMY_PARTICIPANTS2, new Date(2021,11,2,16,00), new Date(2021,11,2,12,0), "Luigi"),
            new Meeting(rooms[8], DUMMY_PARTICIPANTS3, new Date(2021,11,5,10,30), new Date(2021,11,2,12,0), "Mario")
    );



    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}

