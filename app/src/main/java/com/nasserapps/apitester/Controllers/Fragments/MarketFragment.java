package com.nasserapps.apitester.Controllers.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nasserapps.apitester.Controllers.Adapters.MarketAdapter;
import com.nasserapps.apitester.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

import java.util.ArrayList;
import java.util.Collections;

public class MarketFragment extends Fragment {

    public static final int INDEX = 0;
    public static final int SUMMARY = 1;
    public static final int NEWS = 2;

    private Object[] mDataset = {new Ticker(), new ArrayList<Ticker>(),new ArrayList<Ticker>()};
    private int[] mDatasetTypes = {INDEX,SUMMARY,SUMMARY};

    private RecyclerView mIndexWatchListView;
    private User mUser;

    //"BZJ16.NYM"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1.1 Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_stocks_list, container, false);

        //1.4 The IndexWatchList Card
        mIndexWatchListView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mIndexWatchListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIndexWatchListView.setHasFixedSize(true);
        mIndexWatchListView.setItemAnimator(new DefaultItemAnimator());

        //2.0 Initialize variables for display
        mUser = User.getUser(getContext());

        //setDisplay();


        //4.0 Get the updated data and set the display with the updated data
        //getUpdatedData();

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stocks_list, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onResume() {
        super.onResume();
        mDataset[0]= Tools.getStockFromList("BRES", mUser.getAllStocks());
        ArrayList<Ticker> topGainers = mUser.getAllStocks();
        Tools.sort(topGainers, "Gain");
        mDataset[1]= new ArrayList<>(topGainers.subList(0,5));
        Collections.reverse(topGainers);
        mDataset[2]= new ArrayList<>(topGainers.subList(0,5));
        //3.0 Set the Display with Initial Data
        setDisplay();
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
        mIndexWatchListView.setAdapter(new MarketAdapter(getActivity().getApplicationContext(), mDataset,mDatasetTypes));
    }

//    private void getUpdatedData() {
//
//        if(isNetworkAvailable()) {
//
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(mDataSource.getAPIURL(mWallet.getAPIKey()))
//                    .build();
//
//            Call call = client.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            alertUserAboutError();
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                        }
//                    });
//                    try {
//                        final String jsonData = response.body().string();
//                        if (response.isSuccessful()) {
//                            mWallet.updateWatchList(jsonData);
//                            mStockWatchList = mWallet.getWatchList();
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    updateDisplay();
//                                    mDataSource.saveWallet(mWallet);
//                                    //Log.e("zxc", "mWallet. String is: " + mDataSource.getWallet().getAPIKey() + "");
//                                }
//                            });
//                        } else {
//                            alertUserAboutError();
//                        }
//                    } catch (IOException e) {
//                        Log.e("error", "IO Exception caught", e);
//                    } catch (JSONException e) {
//                        Log.e("error", "JSON Exception caught", e);
//                    }
//                }
//            });
//        }
//
//        else{
//            Snackbar.make(mIndexWatchListView, "No Network Connection.", Snackbar.LENGTH_LONG).show();
//        }
//    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }

}
