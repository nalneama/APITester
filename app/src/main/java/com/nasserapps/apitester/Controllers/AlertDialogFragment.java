package com.nasserapps.apitester.Controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Nasser on 10/1/15.
 * Dialog to show error.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("Ok", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
