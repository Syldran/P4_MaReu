package fr.p4.mareu.model;

import java.util.List;

public class Meeting {
    private Room mRoom;
    private int mColor;
    private List<Employee> mParticipants;
    private TimeRange mDuration;
    private String mSubject;

    public Meeting(Room room, int color, List<Employee> participants, String subject, TimeRange duration) {
        mRoom = room;
        mColor = color;
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

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
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

    public List<Employee> getParticipants() {
        return (List<Employee>) mParticipants;
    }

    public void setParticipants(List<Employee> participants) {
        mParticipants = participants;
    }
}
