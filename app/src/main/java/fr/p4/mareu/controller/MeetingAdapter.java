package fr.p4.mareu.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.p4.mareu.R;
import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final ArrayList<Meeting> mMeetings;

    public MeetingAdapter(ArrayList<Meeting> meetings){
        this.mMeetings=meetings;
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

        public void displayMeeting(Meeting meeting){
            title.setText("Réunion - "+ meeting.getDateStart()+" - "+meeting.getSubject());
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
