package fr.p4.mareu.model;

import java.util.ArrayList;

public class Room {
    private String mId;
    private ArrayList<TimeRange> mUnavailability;

    public Room(String id) {
        mId = id;
        mUnavailability = new ArrayList<TimeRange>(0);
    }

    public Room(String id, TimeRange unavailability){
        mId = id;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}

