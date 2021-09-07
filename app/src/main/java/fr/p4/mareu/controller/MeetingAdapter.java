package fr.p4.mareu.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.p4.mareu.R;
import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private Context mContext;

    public MeetingAdapter(List<Meeting> meetings, Context context) {
        this.mMeetings = meetings;
        mContext = context;
    }


    @NonNull
    @Override
    public MeetingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        Meeting meeting=mMeetings.get(position);
        holder.displayMeeting(meeting);
        holder.itemView.setOnClickListener(v -> mContext.startActivity(new Intent()));
    }

    @Override
    public int getItemCount() {

        return mMeetings.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public final TextView mailList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.item_meeting_title);
            mailList=itemView.findViewById(R.id.item_meeting_mail_list);
        }

        public void displayMeeting(Meeting meeting) {
            Log.i("TRACE1", String.valueOf(meeting.getDuration().getStart()));
            title.setText("Réunion - " + meeting.getDuration().getStart() + " - " + meeting.getSubject());
            mailList.setText(displayParticipants(meeting.getParticipants()));
        }

        private CharSequence displayParticipants(ArrayList<Employee> participants) {
            CharSequence meetingMails="";
            for (Employee employee:participants
            ) {
                meetingMails += employee.getMail()+", ";
            }
            return meetingMails;
        }
    }
}
