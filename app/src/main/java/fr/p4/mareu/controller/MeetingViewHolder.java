package fr.p4.mareu.controller;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.R;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.model.Employee;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.utils.RecyclerViewHolderListener;

public class MeetingViewHolder extends RecyclerView.ViewHolder {
    public final ImageView meetingColor;
    public final TextView title;
    public final TextView mailList;
    public final ImageButton deleteButton;
    private final MeetingApiService mApiService = DI.getMeetingApiService();

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        meetingColor = itemView.findViewById(R.id.photo);
        title = itemView.findViewById(R.id.item_meeting_title);
        mailList = itemView.findViewById(R.id.item_meeting_mail_list);
        deleteButton = itemView.findViewById(R.id.itemDeleteButton);
    }

    public void displayMeeting(Meeting meeting, RecyclerViewHolderListener listener) {
        meetingColor.setBackgroundTintList(ColorStateList.valueOf(meeting.getColor()));
        title.setText(meeting.getSubject() + " - " + meeting.getDuration().getStart().get(Calendar.HOUR_OF_DAY) + "h" + meeting.getDuration().getStart().get(Calendar.MINUTE) + " - " + meeting.getRoom().getId());
        mailList.setText(displayParticipants(meeting.getParticipants()));
        deleteButton.setOnClickListener(view -> listener.onItemClicked(this, meeting, getAdapterPosition()));
    }

    private CharSequence displayParticipants(List<Employee> participants) {
        CharSequence meetingMails = "";
        for (int i = 0; i < participants.size(); i++) {
            if (i == (participants.size() - 1)) {
                meetingMails += participants.get(i).getMail();
            } else {
                meetingMails += participants.get(i).getMail() + ", ";
            }
        }
        return meetingMails;
    }
}
