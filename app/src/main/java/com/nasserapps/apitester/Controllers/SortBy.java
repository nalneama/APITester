package com.nasserapps.apitester.Controllers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.nasserapps.apitester.Model.Ticker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortBy extends AlertDialogFragment {

    private List<Ticker> mStocks;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Sort by:")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(new String[]{"Dec", "Ace"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Collections.sort(mStocks, new Comparator<Ticker>() {
                                    @Override
                                    public int compare(Ticker stock1, Ticker stock2) {
                                        return (int) (stock2.getPrice() - stock1.getPrice());
                                    }
                                });
                                break;
                            case 1:
                                Collections.sort(mStocks, new Comparator<Ticker>() {
                                    @Override
                                    public int compare(Ticker stock1, Ticker stock2) {
                                        return (int) (stock1.getPrice() - stock2.getPrice());
                                    }
                                });
                                break;
                        }
                    }
                });

        return builder.create();
    }
}
