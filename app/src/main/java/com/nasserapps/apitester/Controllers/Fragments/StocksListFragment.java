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

import com.nasserapps.apitester.AI.CustomNotificationStatement.Checklist;
import com.nasserapps.apitester.AI.CustomNotificationStatement.ExpressionParser;
import com.nasserapps.apitester.AI.CustomNotificationStatement.Rule;
import com.nasserapps.apitester.Controllers.Adapters.StockAdapter;
import com.nasserapps.apitester.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.apitester.Controllers.InProgress.EditStockListActivity;
import com.nasserapps.apitester.Controllers.InProgress.MainActivity;
import com.nasserapps.apitester.Controllers.InProgress.RulesActivity;
import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.UserData;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.util.SimpleDividerItemDecoration;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StocksListFragment extends Fragment {

    private DataSource mDataSource;
    private List<Ticker> mStockWatchList;
    private Wallet mWallet;
    private UserData mUserData;

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
        mStockWatchListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        //2.0 Initialize variables for display
        //2.1 The DataSource (Object responsible to work with the application memory)
        mDataSource = new DataSource(getActivity());
        mUserData = new UserData(getActivity());
        //2.2 The Wallet
        mWallet = new Wallet();
        //2.2a Checking if data is available in memory: if yes, then get wallet data from memory
        if(mUserData.isUserDataAvailable()) {
            mWallet = mDataSource.getWallet();
            mStockWatchList =mWallet.getWatchList();
        }
        //2.2b Else,this is first time opening of app, set the wallet to initial data
        else{
            // mWallet.setInitialWatchList();
            mStockWatchList = new ArrayList<>();
            String[] companies = getActivity().getResources().getStringArray(R.array.Companies_API_Codes);
            for (String code:companies){
                mStockWatchList.add(new Ticker(code));
            }
            mWallet.setInitialWatchList(mStockWatchList);
        }


        //3.0 Set the Display with Initial Data
        setDisplay();


        //4.0 Get the updated data and set the display with the updated data
        getUpdatedData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDisplay();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stocks_list, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void updateDisplay() {
        if(mUserData.isUserDataAvailable()) {
            setDisplay();
        }
        else {
            mStockWatchListView.swapAdapter(new StockAdapter(getActivity().getApplicationContext(),mStockWatchList), false);
        }
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

    public void setDisplay(){
        mStockWatchListView.setAdapter(new StockAdapter(getActivity().getApplicationContext(), mStockWatchList));
    }

    private void getUpdatedData() {

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mDataSource.getAPIURL(mWallet.getAPIKey()))
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
                            mWallet.updateWatchList(jsonData);
                            mStockWatchList = mWallet.getWatchList();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    mDataSource.saveWallet(mWallet);
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

            if (id == R.id.detailed_view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                return true;
            }
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
            if (id == R.id.add_rules) {
                Intent i = new Intent(getActivity(), RulesActivity.class);
                startActivity(i);
                return true;
            }

            if (id == R.id.evaluate_stocks) {
                //TODO Filter stocks by checklists
                ArrayList<Rule> rules = new ArrayList<>();
                rules.add(new ExpressionParser().getRule("PE Ratio", "<", "15.0"));
                rules.add(new ExpressionParser().getRule("PE Ratio", ">","10.0"));
                Checklist checklist = new Checklist(rules);
                mStockWatchList=checklist.getPassingStocks(mWallet);
                updateDisplay();
                return true;
            }

            if (id == R.id.sorting_options) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setSingleChoiceItems(new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price" }, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mStockWatchList=mWallet.sort(mStockWatchList, "A-Z");
                                break;
                            case 1:
                                mStockWatchList=mWallet.sort(mStockWatchList,"Book Value");
                                break;
                            case 2:
                                mStockWatchList=mWallet.sort(mStockWatchList,"Gain");
                                break;
                            case 3:
                                mStockWatchList=mWallet.sort(mStockWatchList,"PE Ratio");
                                break;
                            case 4:
                                mStockWatchList= mWallet.sort(mStockWatchList,"Price");
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
