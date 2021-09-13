package fr.p4.mareu.controller;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.R;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;

public class MeetingViewHolder extends RecyclerView.ViewHolder {
    private MeetingApiService mApiService = DI.getMeetingApiService();
    public final TextView title;
    public final TextView mailList;
    public final ImageButton deleteButton;

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.item_meeting_title);
        mailList = itemView.findViewById(R.id.item_meeting_mail_list);
        deleteButton = itemView.findViewById(R.id.itemDeleteButton);
    }

    public void displayMeeting(Meeting meeting) {
        title.setText("Réunion - " + meeting.getDuration().getStart() + " - " + meeting.getSubject());
        mailList.setText(displayParticipants(meeting.getParticipants()));
        deleteButton.setOnClickListener(view -> mApiService.deleteMeeting(meeting));
    }

    private CharSequence displayParticipants(ArrayList<Employee> participants) {
        CharSequence meetingMails = "";
        for (Employee employee : participants
        ) {
            meetingMails += employee.getMail() + ", ";
        }
        return meetingMails;
    }
}
