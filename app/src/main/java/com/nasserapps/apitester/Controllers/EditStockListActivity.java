package com.nasserapps.apitester.Controllers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nasserapps.apitester.R;

import java.util.ArrayList;
import java.util.List;

public class EditStockListActivity extends AppCompatActivity {

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<String> mStocksViewList;
    private ArrayList<String> mStocksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStocksList= new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStocksViewList = new ArrayList<>();
        mStocksViewList.add("MERS - Al-Meera");
        mStocksViewList.add("BRES - Barwa");
        mStocksViewList.add("QIB - Qatar Islamic Bank");

        // Find the ListView resource.
        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_stock_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new StockListAdapter(mStocksViewList));

        // When item is tapped, toggle checked properties of CheckBox and Planet.
        /*mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id) {

                /*Planet planet = listAdapter.getItem(position);
                planet.toggleChecked();
                PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(planet.isChecked());
            }
        });*/


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

            mStockCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStockCheckbox.isChecked()){
                        mStocksList.add(mStockName);
                        Snackbar.make(v, mStocksList.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    }
                    else {
                        mStocksList.remove(mStockName);
                        Snackbar.make(v, mStocksList.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }
    }

}
