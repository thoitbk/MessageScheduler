package com.thoitbk.messagescheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CountingFragment countingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countingFragment = (CountingFragment) getSupportFragmentManager().findFragmentByTag("counting_fragment");
        if (countingFragment == null) {
            countingFragment = new CountingFragment();
            getSupportFragmentManager().beginTransaction().add(countingFragment, "counting_fragment").commit();
        }

        TextView progress = (TextView) findViewById(R.id.progress);
        countingFragment.setCountingTextView(progress);
        countingFragment.setContext(this);
    }

    public void onStartClick(View view) {
        CountingFragment fragment = (CountingFragment) getSupportFragmentManager().findFragmentByTag("counting_fragment");
        if (fragment != null) {
            fragment.startCounting();
        }
    }

    public void onStopClick(View view) {
        CountingFragment fragment = (CountingFragment) getSupportFragmentManager().findFragmentByTag("counting_fragment");
        if (fragment != null) {
            fragment.stopCounting();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countingFragment != null) {
            countingFragment.setCountingTextView(null);
            countingFragment = null;
        }
    }
}
