package fr.p4.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static fr.p4.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.p4.mareu.controller.MainActivity;
import fr.p4.mareu.utils.DeleteViewAction;
import fr.p4.mareu.utils.SpinnerItemCountAssertion;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {
    private static final int ROOMS_COUNT = 10;
    private static int ITEMS_COUNT = 3;
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        ActivityScenario scenario = activityScenarioRule.getScenario();
        assertThat(scenario, notNullValue());
    }

    @Test
    public void meetingListNotEmpty_and_RoomsCountValue() {
        onView(ViewMatchers.withId(R.id.main_meeting_recyclerview)).check(matches(hasMinimumChildCount(1)));
        onView(ViewMatchers.withId(R.id.main_add_meeting)).perform(click());
        assertThat(ViewMatchers.withId(R.layout.activity_add_meeting), notNullValue());
        //set Date
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_date)).perform(click());
        onView(withId(R.id.confirm_button)).perform(click());

        //set Start
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_time_start)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("7"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());

        //set End
        onView(withId(R.id.add_meeting_edit_text_time_end)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("8"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());

        onView(withId(R.id.spinnerRooms)).check(SpinnerItemCountAssertion.withItemCount(ROOMS_COUNT));
    }

    @Test
    public void myMeetingList_delete_and_add_with_success() {
        onView(ViewMatchers.withId(R.id.main_meeting_recyclerview)).check(withItemCount(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.main_meeting_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(ViewMatchers.withId(R.id.main_meeting_recyclerview)).check(withItemCount(ITEMS_COUNT - 1));
        ITEMS_COUNT -= 1;
    }

    @Test
    public void myMeetingList_add_with_success_and_make_room_unavailability() {
        //click button to Add Meeting
        onView(ViewMatchers.withId(R.id.main_add_meeting)).perform(click());
        assertThat(ViewMatchers.withId(R.layout.activity_add_meeting), notNullValue());

        //set Subject and Participants
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_subject)).perform(typeText("Test"));
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_employees_list)).perform(typeText("Guillaume"));
        onView(ViewMatchers.withId(R.id.add_meeting_buttonAddEmployees)).perform(click());

        //set Date
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_date)).perform(click());
        onView(withId(R.id.confirm_button)).perform(click());

        //set Start
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_time_start)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("7"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());

        //set End
        onView(withId(R.id.add_meeting_edit_text_time_end)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("8"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());

        //confirm
        onView(withId(R.id.add_meeting_buttonAddMeeting)).perform(click());
        onView(withId(R.id.main_meeting_recyclerview)).check(withItemCount(ITEMS_COUNT + 1));
        ITEMS_COUNT += 1;

        //set Meeting 2 with same timeslot
        onView(ViewMatchers.withId(R.id.main_add_meeting)).perform(click());
        assertThat(ViewMatchers.withId(R.layout.activity_add_meeting), notNullValue());

        //set Date
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_date)).perform(click());
        onView(withId(R.id.confirm_button)).perform(click());

        //set Start
        onView(ViewMatchers.withId(R.id.add_meeting_edit_text_time_start)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("7"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());

        //set End
        onView(withId(R.id.add_meeting_edit_text_time_end)).perform(click());
        onView(withId(R.id.material_timepicker_mode_button)).perform(click());
        onView(ViewMatchers.withId(R.id.material_hour_text_input)).perform(click());
        onView(ViewMatchers.isFocused()).perform(typeText("8"));
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());
        onView(withId(R.id.spinnerRooms)).check(SpinnerItemCountAssertion.withItemCount(ROOMS_COUNT - 1));
    }
}