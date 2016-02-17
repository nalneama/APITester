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

import com.nasserapps.apitester.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.UserData;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;
import java.util.List;

public class IndexesListFragment extends Fragment {

    DataSource mDataSource;
    List<Ticker> mStockWatchList;
    List<Ticker> mIndexWatchList;
    private UserData mUserData;
    Wallet mWallet;

    private RecyclerView mIndexWatchListView;
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
        //2.1 The DataSource (Object responsible to work with the application memory)
        mDataSource = new DataSource(getActivity());
        mUserData = new UserData(getActivity());
        //2.2 The Wallet
        mWallet = new Wallet();
        //2.2a Checking if data is available in memory: if yes, then get wallet data from memory
        if(mUserData.isUserDataAvailable()) {
            //mWallet = mDataSource.getWallet();
            //mStockWatchList =mWallet.getWatchList();
        }
        //2.2b Else,this is first time opening of app, set the wallet to initial data
        else{
            // mWallet.setInitialWatchList();
            mStockWatchList = new ArrayList<>();
            String[] companies = getActivity().getResources().getStringArray(R.array.Companies_API_Codes);
            for (String code:companies){
                mStockWatchList.add(new Ticker(code));
            }
            //mWallet.setInitialWatchList(mStockWatchList);
        }


        //3.0 Set the Display with Initial Data
        setDisplay();


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
        updateDisplay();

    }

    public void updateDisplay() {
        if(mUserData.isUserDataAvailable()) {
            setDisplay();
        }
        else {
            //mStockWatchListView.swapAdapter(new StockAdapter(getActivity().getApplicationContext(),mStockWatchList), false);
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
        //mStockWatchListView.setAdapter(new StockAdapter(getActivity().getApplicationContext(), mStockWatchList));
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
