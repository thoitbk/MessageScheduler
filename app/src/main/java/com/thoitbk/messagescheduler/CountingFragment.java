package com.thoitbk.messagescheduler;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountingFragment extends Fragment implements ProgressDialogFragment.OnProgressDialogCancelListener {

    private CountingTask countingTask = new CountingTask();
    private WeakReference<TextView> countingTextView;
    private WeakReference<Context> context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return null;
    }

    public void setCountingTextView(TextView textView) {
        countingTextView = new WeakReference<TextView>(textView);
    }

    public void setContext(Context c) {
        context = new WeakReference<Context>(c);
    }

    public void startCounting() {
        if (countingTask.getStatus() != AsyncTask.Status.RUNNING || countingTask.isCancelled()) {
            countingTask = new CountingTask();
            countingTask.execute();
        }
    }

    public void stopCounting() {
        if (countingTask.getStatus() != AsyncTask.Status.RUNNING) return;
        if (!countingTask.isCancelled()) {
            countingTask.cancel(true);
        }
    }

    @Override
    public void onProgressDialogCancel() {
        stopCounting();
    }

    public class CountingTask extends AsyncTask<Void, Integer, Void> {

        private ProgressDialogFragment dialogFragment;

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView progress = countingTextView.get();
            progress.setText("Finish");
            dialogFragment = (ProgressDialogFragment) getFragmentManager().findFragmentByTag("dialog_fragment");
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            TextView progress = countingTextView.get();
            progress.setText(String.valueOf(values[0]));
            dialogFragment = (ProgressDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("dialog_fragment");
            if (dialogFragment != null) {
                dialogFragment.setProgress(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                if (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                        publishProgress(i + 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialogFragment = (ProgressDialogFragment) getFragmentManager().findFragmentByTag("dialog_fragment");
            if (dialogFragment == null) {
                dialogFragment = new ProgressDialogFragment();
            }

            dialogFragment.setOnProgressDialogCancelListener(CountingFragment.this);
            dialogFragment.show(getFragmentManager(), "dialog_fragment");
        }
    }
}
