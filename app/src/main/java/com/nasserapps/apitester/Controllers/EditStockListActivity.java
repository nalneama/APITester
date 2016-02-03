package com.nasserapps.apitester.Controllers;

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
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditStockListActivity extends AppCompatActivity {

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<String> mAllStocksList;
    private ArrayList<Ticker> mWatchList;
    private Wallet mWallet;
    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDataSource = new DataSource(getApplicationContext());
        mWallet = mDataSource.getWallet();

        mWatchList = new ArrayList<>();
        mWatchList =(ArrayList) mWallet.getWatchList();

        mAllStocksList = new ArrayList<>(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.Companies_Names)));

        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_stock_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new StockListAdapter(mAllStocksList));


        //Filter items by three parameters: Islamic, Mixed, Non-Islamic

    }

    private class StockListAdapter extends RecyclerView.Adapter<StockListHolder> {

        private List<String> stocks;

        public StockListAdapter(ArrayList<String> stocksList) {
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
            String stock = stocks.get(position);
            holder.bindStock(stock);
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

        public void bindStock(String stock){
            mStockName=stock;
            mStockNameView.setText(mStockName);
            for(Ticker ticker:mWatchList){
                if(ticker.getAPICode().contains(stock.substring(0,4))){
                    mStockCheckbox.setChecked(true);
                }
            }

            mStockCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStockCheckbox.isChecked()){
                        mWatchList.add(new Ticker(mStockName.substring(0, 4) + ".QA"));
                    }
                    else {
                        Ticker stock= new Ticker();
                        for(Ticker ticker:mWatchList){
                            if(ticker.getAPICode().contains(mStockName.substring(0,4))){
                                stock=ticker;
                            }
                        }
                        mWatchList.remove(stock);
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWallet.setWatchList(mWatchList);
        mDataSource.saveWallet(mWallet);
    }
}
