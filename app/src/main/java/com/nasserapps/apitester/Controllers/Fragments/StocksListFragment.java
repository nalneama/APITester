package com.nasserapps.apitester.Controllers.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nasserapps.apitester.Controllers.Activities.EditStockListActivity;
import com.nasserapps.apitester.Controllers.Adapters.StockAdapter;
import com.nasserapps.apitester.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StocksListFragment extends Fragment {

    private ArrayList<Ticker> mStockWatchList;
    private User mUser;

    private RecyclerView mStockWatchListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1.1 Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stocks_list, container, false);
        //1.2 The StockWatchList Card
        mStockWatchListView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mStockWatchListView.setHasFixedSize(true);
        mStockWatchListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStockWatchListView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Get the user
        mUser = User.getUser(getActivity());
        mStockWatchList = new ArrayList<>();
        mStockWatchList = mUser.getWatchList();

        //3.0 Set the Display with Data
        updateDisplay();

        //4.0 Get the updated data and set the display with the updated data
        //getUpdatedData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stocks_list, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void updateDisplay() {
            mStockWatchListView.swapAdapter(new StockAdapter(getActivity().getApplicationContext(),mStockWatchList), false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable =false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    private void getUpdatedData() {

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mUser.getAPIURL())
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertUserAboutError();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        final String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mUser.updateStocksData(jsonData);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mStockWatchList=mUser.getWatchList();
                                    updateDisplay();
                                    //Log.e("zxc", "mWallet. String is: " + mDataSource.getWallet().getAPIKey() + "");
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e("error", "IO Exception caught", e);
                    } catch (JSONException e) {
                        Log.e("error", "JSON Exception caught", e);
                    }
                }
            });
        }

        else{
            Snackbar.make(mStockWatchListView,"No Network Connection.",Snackbar.LENGTH_LONG).show();
        }
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();


            if (id == R.id.refresh_data) {
                Snackbar.make(this.mStockWatchListView, "Refreshing Data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getUpdatedData();
                return true;
            }

            if (id == R.id.add_stocks) {
                Intent i = new Intent(getActivity(), EditStockListActivity.class);
                startActivity(i);
                return true;
            }

            if (id == R.id.evaluate_stocks) {
                final Checklist checklist = mUser.getChecklists().get(0);
                String[] sortingOptions= new String[1];
                sortingOptions[0]=checklist.getChecklistName();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Evaluate using")
                        .setSingleChoiceItems(sortingOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStockWatchList=checklist.getPassingStocks(mUser);
                        dialog.dismiss();
                        updateDisplay();
                    }
                });
                dialog.create().show();
                return true;
            }

            if (id == R.id.sorting_options) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                String[] sortingOptions = new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price" };
                int index = Arrays.asList(sortingOptions).indexOf(mUser.getUserData().getSortPreference());
                dialog.setTitle("Sort by")
                        .setSingleChoiceItems(sortingOptions,index , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mStockWatchList= Tools.sort(mUser.getWatchList(), "A-Z");
                                mUser.getUserData().setSortPreference("A-Z");
                                break;
                            case 1:
                                mStockWatchList=Tools.sort(mUser.getWatchList(),"Book Value");
                                mUser.getUserData().setSortPreference("Book Value");
                                break;
                            case 2:
                                mStockWatchList=Tools.sort(mUser.getWatchList(),"Gain");
                                mUser.getUserData().setSortPreference("Gain");
                                break;
                            case 3:
                                mStockWatchList=Tools.sort(mUser.getWatchList(),"PE Ratio");
                                mUser.getUserData().setSortPreference("PE Ratio");
                                break;
                            case 4:
                                mStockWatchList= Tools.sort(mUser.getWatchList(),"Price");
                                mUser.getUserData().setSortPreference("Price");
                                break;}
                        dialog.dismiss();
                        updateDisplay();
                    }
                });
                dialog.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
