package com.thoitbk.messagescheduler;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {

    private ProgressDialog dialog;

    private OnProgressDialogCancelListener listener;

    public ProgressDialogFragment() {

    }

    public void setOnProgressDialogCancelListener(OnProgressDialogCancelListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Counting");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setIndeterminate(false);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    public void setProgress(int progress) {
        dialog.setProgress(progress);
    }

//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        if (countingFragment != null) {
//            countingFragment.stopCounting();
//        }
//    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (listener != null) {
            listener.onProgressDialogCancel();
        }
    }

    public interface OnProgressDialogCancelListener {
        public void onProgressDialogCancel();
    }
}