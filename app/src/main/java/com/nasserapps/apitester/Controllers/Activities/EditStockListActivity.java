package com.nasserapps.apitester.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

import java.util.ArrayList;
import java.util.List;

public class EditStockListActivity extends AppCompatActivity {

    //Filtering lists
    //https://www.youtube.com/watch?v=c9yC8XGaSv4

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<Ticker> mAllStocksList;
    private User mUser;
    private CheckBox mIslamicCheckBox;
    private CheckBox mNonIslamicCheckBox;
    private CheckBox mMixedCheckBox;
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser=User.getUser(this);
        mAllStocksList = new ArrayList<>();
        Tools.sort(mAllStocksList, "A-Z");
        for(Ticker ticker:mUser.getAllStocks() ){
                if (ticker.isIslamic()){mAllStocksList.add(ticker);}
        }
        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_stock_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new StockListAdapter(mAllStocksList));

        mIslamicCheckBox = (CheckBox)findViewById(R.id.islamicCheckBox);
        mMixedCheckBox = (CheckBox)findViewById(R.id.mixedCheckBox);
        mNonIslamicCheckBox = (CheckBox)findViewById(R.id.nonIslamicCheckBox);
        //Filter items by three parameters: Islamic, Mixed, Non-Islamic
        mIslamicCheckBox.setChecked(true);
        mMixedCheckBox.setChecked(false);
        mNonIslamicCheckBox.setChecked(false);

        onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAllStocksList= new ArrayList<>();
                for(Ticker ticker:mUser.getAllStocks() ){
                    if(mIslamicCheckBox.isChecked()){
                        if (ticker.isIslamic()){mAllStocksList.add(ticker);}
                    }
                    if(mMixedCheckBox.isChecked()){
                        if (ticker.isMixed()){mAllStocksList.add(ticker);}
                    }
                    if(mNonIslamicCheckBox.isChecked()){
                        if (ticker.isNotIslamic()){mAllStocksList.add(ticker);}
                    }
                }

                mEditStocksRecyclerView.swapAdapter(new StockListAdapter(mAllStocksList), true);

            }
        };

        mIslamicCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
        mNonIslamicCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
        mMixedCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
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

        public StockListHolder(View itemView) {
            super(itemView);
            mStockNameView = (TextView)itemView.findViewById(R.id.edit_stock_name);
            mStockCheckbox = (CheckBox) itemView.findViewById(R.id.edit_stock_checkbox);
        }

        public void bindStock(final Ticker stock){
            mStockNameView.setText(stock.getName());
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
                    DataSource mDatasource = new DataSource(getBaseContext());
                    mDatasource.updateStock(stock);
                }

            });
        }
    }


}
