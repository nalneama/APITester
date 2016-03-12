package com.nasserapps.saham.Controllers.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity().getBaseContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("Ok", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
