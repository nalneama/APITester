package com.nasserapps.saham.Controllers.Fragments;

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

import com.nasserapps.saham.Controllers.Activities.EditStockListActivity;
import com.nasserapps.saham.Controllers.Adapters.StockCursorAdapter;
import com.nasserapps.saham.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.saham.Model.Checklists.Checklist;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.Tools;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StocksListCursorFragment extends Fragment {

    private ArrayList<Stock> mStockWatchList;
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
            mStockWatchListView.swapAdapter(new StockCursorAdapter(getActivity().getApplicationContext(), mStockWatchList), false);
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
                    .url(getResources().getString(R.string.API_url))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //alertUserAboutError();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
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
                            //alertUserAboutError();
                            Log.e("zxc", "onResponse else");
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
                String[] sortingOptions = Tools.sortingOptions;
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
                                break;
                            case 5:
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
