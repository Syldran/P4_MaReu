package fr.p4.mareu.model;

import java.util.Calendar;

public class TimeRange {
    private Calendar mStart;
    private Calendar mEnd;

    public TimeRange(Calendar start, Calendar end) {
        mStart = start;
        mEnd = end;
    }

    public Calendar getStart() {
        return mStart;
    }

    public void setStart(Calendar start) {
        mStart = start;
    }

    public Calendar getEnd() {
        return mEnd;
    }

    public void setEnd(Calendar end) {
        mEnd = end;
    }

    public boolean intersect(TimeRange duration) {
        return duration.getStart().before(mEnd) && duration.getEnd().after(mStart);
    }
}
