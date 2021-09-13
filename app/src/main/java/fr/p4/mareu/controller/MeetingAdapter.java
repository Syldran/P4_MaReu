package fr.p4.mareu.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.p4.mareu.R;
import fr.p4.mareu.model.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    private List<Meeting> mMeetings = new ArrayList<>();
    private Context mContext;


    public MeetingAdapter(List<Meeting> meetings, Context context) {
        this.mMeetings = meetings;
        mContext = context;
    }


    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting=mMeetings.get(position);
        holder.displayMeeting(meeting);
        //holder.itemView.setOnClickListener(v -> mContext.startActivity(new Intent()));
    }

    @Override
    public int getItemCount() {

        return mMeetings.size();
    }
}
