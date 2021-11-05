package com.hk.kbottomnavigation.verticalstepperform.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.hk.kbottomnavigation.R;

public class BackConfirmationFragment extends DialogFragment {

    private DialogInterface.OnClickListener onConfirmBack;
    private DialogInterface.OnClickListener onNotConfirmBack;

    public void setOnConfirmBack(DialogInterface.OnClickListener onConfirmBack) {
        this.onConfirmBack = onConfirmBack;
    }

    public void setOnNotConfirmBack(DialogInterface.OnClickListener onNotConfirmBack) {
        this.onNotConfirmBack = onNotConfirmBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.vertical_form_stepper_form_discard_question)
                .setMessage(R.string.vertical_form_stepper_form_info_will_be_lost)
                .setNegativeButton(R.string.vertical_form_stepper_form_discard_cancel, onConfirmBack)
                .setPositiveButton(R.string.vertical_form_stepper_form_discard, onNotConfirmBack);
        return builder.create();
    }
}