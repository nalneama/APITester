package com.nasserapps.apitester.Controllers.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nasserapps.apitester.Controllers.Adapters.MarketAdapter;
import com.nasserapps.apitester.Controllers.Dialogs.AlertDialogFragment;
import com.nasserapps.apitester.Model.Database.JSONParser;
import com.nasserapps.apitester.Model.Market;
import com.nasserapps.apitester.Model.Stock;
import com.nasserapps.apitester.Model.Tools;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MarketFragment extends Fragment {

    public static final int INDEX = 0;
    public static final int SUMMARY = 1;
    public static final int NEWS = 2;

    private Object[] mDataset = {new Market(null, null), new ArrayList<Stock>(),new ArrayList<Stock>()};
    private int[] mDatasetTypes = {MarketAdapter.MARKET,MarketAdapter.SUMMARY,MarketAdapter.SUMMARY};
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
        mIndexWatchListView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mIndexWatchListView.setHasFixedSize(true);
        mIndexWatchListView.setItemAnimator(new DefaultItemAnimator());

        //2.0 Initialize variables for display
        mUser = User.getUser(getContext());

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


        mDataset[0]= mUser.getMarket();
        ArrayList<Stock> topGainers = mUser.getAllStocks();
        Tools.sort(topGainers, "Gain");
        mDataset[1]= new ArrayList<>(topGainers.subList(0,5));
        Collections.reverse(topGainers);
        mDataset[2]= new ArrayList<>(topGainers.subList(0,5));
        //3.0 Set the Display with Initial Data
        setDisplay();
        //TODO save brent data
        //getUpdatedData();

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

    private void getUpdatedData() {

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%3D%22BZJ16.NYM+%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //alertUserAboutError();
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
                            final Stock brent = JSONParser.getBrent(jsonData);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mDataset[0]=brent;
                                    setDisplay();
                                }
                            });
                        } else {
                            //alertUserAboutError();
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
        }
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }

}
