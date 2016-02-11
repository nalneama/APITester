package com.nasserapps.apitester.Controllers.InProgress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditStockListActivity extends AppCompatActivity {

    //Filtering lists
    //https://www.youtube.com/watch?v=c9yC8XGaSv4

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<Ticker> mAllStocksList;
    private ArrayList<Ticker> mWatchList;
    private HashMap<String,Ticker> mWatchMap;
    private Wallet mWallet;
    private DataSource mDataSource;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Todo fix right now
        mUser= new User(getApplicationContext());

        mAllStocksList = new ArrayList<>();
        mAllStocksList = mUser.getAllStocks();

        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_stock_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new StockListAdapter(mAllStocksList));


        //Filter items by three parameters: Islamic, Mixed, Non-Islamic

    }

    private class StockListAdapter extends RecyclerView.Adapter<StockListHolder> {

        private List<Ticker> stocks;

        public StockListAdapter(ArrayList<Ticker> stocksList) {
            stocks=stocksList;
        }

        @Override
        public StockListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_stock_names,parent,false);
            return new StockListHolder(view);
        }

        @Override
        public void onBindViewHolder(StockListHolder holder, int position) {
            Ticker ticker = stocks.get(position);
            holder.bindStock(ticker);
        }

        @Override
        public int getItemCount() {
            return stocks.size();
        }
    }

    private class StockListHolder extends RecyclerView.ViewHolder {

        private TextView mStockNameView;
        private CheckBox mStockCheckbox;
        private String mStockName;

        public StockListHolder(View itemView) {
            super(itemView);
            mStockNameView = (TextView)itemView.findViewById(R.id.edit_stock_name);
            mStockCheckbox = (CheckBox) itemView.findViewById(R.id.edit_stock_checkbox);
        }

        public void bindStock(final Ticker stock){
            mStockName=stock.getAPICode();
            mStockNameView.setText(mStockName);
            mStockCheckbox.setChecked(stock.isInWatchList());

            mStockCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStockCheckbox.isChecked()) {
                        stock.setInWatchList(true);
                    }
                    else {
                        stock.setInWatchList(false);
                    }
                }

            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Save to database
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_stock_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        //Todo fix right now
//        mUser= new User(getApplicationContext());
//
//        //mDataSource = new DataSource(getApplicationContext());
//        mUser= new User(getApplicationContext());
//        mWallet = mDataSource.getWallet();
//
//        mWatchList = new ArrayList<>();
//        mWatchList =(ArrayList) mWallet.getWatchList();
//
//        //*****New addition
//        mWatchMap = new HashMap<>();
//        mWatchMap = Wallet.getWatchMap(mWatchList);
//
//        //*****
//
//        mAllStocksList = new ArrayList<>(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.Companies_Names)));
//
//        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_stock_list_recyclerview);
//        mEditStocksRecyclerView.setHasFixedSize(true);
//        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        mEditStocksRecyclerView.setAdapter(new StockListAdapter(mAllStocksList));
//
//
//        //Filter items by three parameters: Islamic, Mixed, Non-Islamic
//
//    }
//
//    private class StockListAdapter extends RecyclerView.Adapter<StockListHolder> {
//
//        private List<String> stocks;
//
//        public StockListAdapter(ArrayList<String> stocksList) {
//            stocks=stocksList;
//        }
//
//        @Override
//        public StockListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View view = layoutInflater.inflate(R.layout.list_item_stock_names,parent,false);
//            return new StockListHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(StockListHolder holder, int position) {
//            String stock = stocks.get(position);
//            holder.bindStock(stock);
//        }
//
//        @Override
//        public int getItemCount() {
//            return stocks.size();
//        }
//    }
//
//    private class StockListHolder extends RecyclerView.ViewHolder {
//
//        private TextView mStockNameView;
//        private CheckBox mStockCheckbox;
//        private String mStockName;
//
//        public StockListHolder(View itemView) {
//            super(itemView);
//            mStockNameView = (TextView)itemView.findViewById(R.id.edit_stock_name);
//            mStockCheckbox = (CheckBox) itemView.findViewById(R.id.edit_stock_checkbox);
//        }
//
//        public void bindStock(String stock){
//            mStockName=stock;
//            mStockNameView.setText(mStockName);
//            if(mWatchMap.containsKey(stock.substring(0,4))){
//                mStockCheckbox.setChecked(true);
//            }
//            else {
//                mStockCheckbox.setChecked(false);
//            }
//
//            mStockCheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mStockCheckbox.isChecked()) {
//                        mWatchList.add(new Ticker(mStockName.substring(0, 4) + ".QA"));
//                    } else {
//                        Ticker stock = new Ticker();
//                        for (Ticker ticker : mWatchList) {
//                            if (ticker.getAPICode().contains(mStockName.substring(0, 4))) {
//                                stock = ticker;
//                            }
//                        }
//                        mWatchList.remove(stock);
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mWallet.setWatchList(mWatchList);
//        mDataSource.saveWallet(mWallet);
//    }

}
