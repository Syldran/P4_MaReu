package fr.p4.mareu.model;

import java.util.ArrayList;

public class Room {
    private int mNumber;
    private ArrayList<TimeRange> mUnavailability;

    public Room(int number) {
        mNumber = number;
        mUnavailability = new ArrayList<TimeRange>(0);
    }

    public Room(int number, TimeRange unavailability){
        mNumber = number;
        mUnavailability.add(unavailability);
    }


    public ArrayList<TimeRange> getUnavailability() {
        return mUnavailability;
    }

    public void setUnavailability(TimeRange duration) {
        mUnavailability.add(duration);
    }

    public void removeUnavailability(TimeRange duration) {
        mUnavailability.remove(duration);
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }
}

