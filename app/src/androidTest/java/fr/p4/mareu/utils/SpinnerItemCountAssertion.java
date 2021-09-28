package fr.p4.mareu.utils;

import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class SpinnerItemCountAssertion implements ViewAssertion {
    private final Matcher<Integer> matcherSpinner;

    public SpinnerItemCountAssertion(Matcher<Integer> matcher) {
        matcherSpinner = matcher;
    }

    public static SpinnerItemCountAssertion withItemCount(int expectedCount){
        return withItemCount(Matchers.is(expectedCount));
    }

    public static SpinnerItemCountAssertion withItemCount(Matcher<Integer> matcher){
        return new SpinnerItemCountAssertion(matcher);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        Spinner spinner = (Spinner) view;
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        Log.i("SpinnerCount", String.valueOf(spinnerAdapter.getCount()));
        Assert.assertThat(spinnerAdapter.getCount(), matcherSpinner);
    }
}
