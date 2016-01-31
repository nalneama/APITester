package com.nasserapps.apitester.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.R;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder>{

    private List<Ticker> mStockList;
    private Context mContext;

    public StockAdapter(Context context, List<Ticker> stockList) {
        mContext=context;
        mStockList = stockList;
    }

    @Override
    public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_stocks,parent,false);
        return new StockHolder(view);
    }

    @Override
    public void onBindViewHolder(StockHolder holder, int position) {
        Ticker stock = mStockList.get(position);
        holder.bindStock(stock);
    }

    @Override
    public int getItemCount() {
        return mStockList.size();
    }

    public class StockHolder extends RecyclerView.ViewHolder{

        //View Declaration
        private TextView mStockNameView;
        private TextView mStockSymbol;
        private TextView mStockPrice;
        private TextView mStockPBValue;
        private TextView mStockPERatio;
        private TextView mStockChange;
        private TextView mStockPercentage;

        private Ticker mStock;

        public StockHolder(View itemView) {
            super(itemView);
            //Find view by ID
            mStockNameView = (TextView)itemView.findViewById(R.id.stockName);
            mStockSymbol = (TextView) itemView.findViewById(R.id.stockSymbol);
            mStockPrice = (TextView) itemView.findViewById(R.id.stockPrice);
            mStockPBValue = (TextView) itemView.findViewById(R.id.bpvalue);
            mStockPERatio = (TextView) itemView.findViewById(R.id.peratio);
            mStockChange = (TextView) itemView.findViewById(R.id.change);
            mStockPercentage = (TextView) itemView.findViewById(R.id.percentage);
        }

        public void bindStock(Ticker stock){
            mStock=stock;
            mStockNameView.setText(mStock.getName());
            mStockSymbol.setText(mStock.getSymbol());
            mStockPrice.setText(mStock.getPrice()+"");
            mStockPBValue.setText("BP: " + mStock.getPBV());
            mStockPERatio.setText("PE: " + mStock.getPERatio());

            mStockPrice.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));



            mStockChange.setText("" + mStock.getChange());
            mStockPercentage.setText("(" + mStock.getPercentage() + ")");

            mStockChange.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));
            mStockPercentage.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));
        }
    }
}

