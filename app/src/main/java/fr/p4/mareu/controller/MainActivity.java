package fr.p4.mareu.controller;

import static fr.p4.mareu.api.DummyMeetingGenerator.rooms;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.p4.mareu.DI.DI;
import fr.p4.mareu.R;
import fr.p4.mareu.api.MeetingApiService;
import fr.p4.mareu.databinding.ActivityMainBinding;
import fr.p4.mareu.model.Meeting;
import fr.p4.mareu.utils.RecyclerViewHolderListener;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private final List<Meeting> mMeetings = new ArrayList<>();
    private MeetingAdapter meetingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFilter();
    }

    private void initUi() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        setButton();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.mainMeetingRecyclerview.setLayoutManager(layoutManager);

        RecyclerViewHolderListener listener = (viewHolder, item, pos) -> {
            Meeting meeting = (Meeting) item;
            mApiService.deleteMeeting(meeting);
            mMeetings.remove(pos);
            meetingAdapter.notifyDataSetChanged();
        };
        meetingAdapter = new MeetingAdapter(mMeetings, this, listener);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.mainMeetingRecyclerview.getContext(), layoutManager.getOrientation());
        mBinding.mainMeetingRecyclerview.addItemDecoration(dividerItemDecoration);
        mBinding.mainMeetingRecyclerview.setAdapter(meetingAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Filter selection
        switch (item.getItemId()) {
            case R.id.filter_date:
                dateDialog();
                return true;
            case R.id.filter_room:
                roomDialog();
                return true;
            case R.id.filter_reset:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dateDialog() {
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsFilteredByDate(cal));
                meetingAdapter.notifyDataSetChanged();
            }
        };
        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        // Show
        datePickerDialog.show();
    }

    private void roomDialog() {
        String[] rooms1 = new String[]{String.valueOf(rooms[0].getId()), String.valueOf(rooms[1].getId()), String.valueOf(rooms[2].getId()), String.valueOf(rooms[3].getId()), String.valueOf(rooms[4].getId()), String.valueOf(rooms[5].getId()), String.valueOf(rooms[6].getId()), String.valueOf(rooms[7].getId()), String.valueOf(rooms[8].getId()), String.valueOf(rooms[9].getId())};
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Rooms");
        dialog.setItems(rooms1, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
            mMeetings.clear();
            mMeetings.addAll(mApiService.getMeetingsFilteredByRoom(rooms[i]));
            meetingAdapter.notifyDataSetChanged();
        });
        dialog.show();
    }

    private void resetFilter() {
        mMeetings.clear();
        mMeetings.addAll(mApiService.getMeetings());
        meetingAdapter.notifyDataSetChanged();
    }

    private void setButton() {
        mBinding.mainAddMeeting.setOnClickListener(v -> startActivity(new Intent(this, AddMeetingActivity.class)));
    }

}