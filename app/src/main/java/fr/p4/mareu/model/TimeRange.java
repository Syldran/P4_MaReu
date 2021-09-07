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
/*        Log.i("intersect", "A : "+duration.getStart().get(Calendar.YEAR)+"    M "+duration.getStart().get(Calendar.MONTH)+"   D : "+duration.getStart().get(Calendar.DAY_OF_MONTH)+"   h : "+duration.getStart().get(Calendar.HOUR_OF_DAY)+"   m : "+duration.getStart().get(Calendar.MINUTE)+" IS AFTER ? ");
        Log.i("intersect mEnd", "A : "+mEnd.get(Calendar.YEAR)+"    M "+mEnd.get(Calendar.MONTH)+"   D : "+mEnd.get(Calendar.DAY_OF_MONTH)+"   h : "+mEnd.get(Calendar.HOUR_OF_DAY)+"   m : "+mEnd.get(Calendar.MINUTE));
        Log.i("intersect", "A : "+duration.getEnd().get(Calendar.YEAR)+"    M "+duration.getEnd().get(Calendar.MONTH)+"   D : "+duration.getEnd().get(Calendar.DAY_OF_MONTH)+"   h : "+duration.getEnd().get(Calendar.HOUR_OF_DAY)+"   m : "+duration.getEnd().get(Calendar.MINUTE)+" IS BEFORE ? ");
        Log.i("intersect mStart", "A : "+mStart.get(Calendar.YEAR)+"    M "+mStart.get(Calendar.MONTH)+"   D : "+mStart.get(Calendar.DAY_OF_MONTH)+"   h : "+mStart.get(Calendar.HOUR_OF_DAY)+"   m : "+mStart.get(Calendar.MINUTE));
*/

        if (duration.getStart().before(mEnd) && duration.getEnd().after(mStart)) {
            return true;
        } else {
            return false;
        }
    }
}
