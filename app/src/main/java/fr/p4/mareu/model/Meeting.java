package fr.p4.mareu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    Room mRoom;
    ArrayList<Employee> mParticipants;

    public Date getDateStart() {
        return mDateStart;
    }

    public void setDateStart(Date dateStart) {
        mDateStart = dateStart;
    }

    public Date getDateEnd() {
        return mDateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        mDateEnd = dateEnd;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    Date mDateStart;
    Date mDateEnd;
    String mSubject;


    public Meeting(Room room, Date dateStart, Date dateEnd, String subject) {
        mRoom = room;
        mParticipants = new ArrayList<Employee>(0);
        mDateEnd = dateEnd;
        mDateStart = dateStart;
        mSubject = subject;
    }

    public Meeting(Room room, ArrayList<Employee> participants, Date dateStart, Date dateEnd, String subject) {
        mRoom = room;
        mParticipants = participants;
        mDateEnd = dateEnd;
        mDateStart = dateStart;
        mSubject = subject;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }

    public ArrayList<Employee> getParticipants() {
        return (ArrayList<Employee>) mParticipants;
    }

    public void setParticipants(ArrayList<Employee> participants) {
        mParticipants = participants;
    }
}
