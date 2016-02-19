package com.nasserapps.apitester.Controllers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasserapps.apitester.Controllers.Activities.StockDetailsActivity;
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
    public StockHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
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

        private TextView mStockHigh;
        private TextView mStockLow;
        private TextView mStockAsk;
        private TextView mStockBid;
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
            mStockPBValue = (TextView) itemView.findViewById(R.id.pbvalue);
            mStockPERatio = (TextView) itemView.findViewById(R.id.peratio);
            mStockChange = (TextView) itemView.findViewById(R.id.change);
            mStockPercentage = (TextView) itemView.findViewById(R.id.percentage);
            mStockAsk = (TextView)itemView.findViewById(R.id.ask);
            mStockBid = (TextView)itemView.findViewById(R.id.bid);
            mStockHigh = (TextView) itemView.findViewById(R.id.high);
            mStockLow = (TextView) itemView.findViewById(R.id.low);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StockDetailsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Symbol",mStock.getSymbol());
                    mContext.startActivity(i);

                }
            });
        }

        public void bindStock(Ticker stock){
            mStock=stock;
            mStockNameView.setText(mStock.getName());
            mStockSymbol.setText(mStock.getSymbol());
            mStockPrice.setText(mStock.getPrice()+"");
            mStockHigh.setText("High: " + mStock.getPrice());//TODO to be fixed
            mStockLow.setText("Low: " + mStock.getPrice());//TODO to be fixed
            mStockPERatio.setText("PE: " + mStock.getPERatio());
            mStockAsk.setText("Ask: " + mStock.getAsk());
            mStockBid.setText("Bid: " + mStock.getBid());
            mStockPBValue.setText("PBV: " + mStock.getPBV());
            mStockPrice.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));



            mStockChange.setText("" + mStock.getChange());
            mStockPercentage.setText("(" + mStock.getPercentage() + ")");

            mStockChange.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));
            mStockPercentage.setTextColor(mContext.getResources().getColor(mStock.getPriceColor()));
        }
    }
}

