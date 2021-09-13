package fr.p4.mareu.model;

import java.util.ArrayList;

public class Meeting {
    private int mId;
    private Room mRoom;
    private ArrayList<Employee> mParticipants;
    private TimeRange mDuration;
    private String mSubject;

    public Meeting(int id, Room room, String subject, TimeRange duration) {
        mId = id;
        mRoom = room;
        mParticipants = new ArrayList<Employee>(0);
        mSubject = subject;
        mDuration = duration;
        mRoom.setUnavailability(duration);
    }

    public Meeting(int id, Room room, ArrayList<Employee> participants, String subject, TimeRange duration) {
        mId = id;
        mRoom = room;
        mParticipants = participants;
        mDuration = duration;
        mSubject = subject;
        mRoom.setUnavailability(duration);
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public TimeRange getDuration() {
        return mDuration;
    }

    public void setDuration(TimeRange duration) {
        mDuration = duration;
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
