package com.nasserapps.saham.Controllers.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nasserapps.saham.Controllers.Adapters.MarketAdapter;
import com.nasserapps.saham.Controllers.Adapters.MarketCursorAdapter;
import com.nasserapps.saham.Model.Database.DataContract;
import com.nasserapps.saham.Model.Market;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.R;

import java.util.ArrayList;

public class MarketCursorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int COMMODITY_LOADER = 0;
    private MarketCursorAdapter mMarketCursorAdapter;

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

        Cursor stockCursor = getActivity().getContentResolver().query(
                DataContract.StocksEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        //3.0 Set the Display with Initial Data
        mIndexWatchListView.setAdapter(new MarketCursorAdapter(getActivity().getApplicationContext(),stockCursor));
        //TODO save brent data
        //getUpdatedData();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri stockUri = DataContract.StocksEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),stockUri,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMarketCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMarketCursorAdapter.swapCursor(null);
    }
}
