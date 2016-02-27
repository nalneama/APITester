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

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketHolder> {

    private final Object[] mDataSet;
    private final int[] mDataSetTypes;
    private Context mContext;
    private List<Ticker> mIndexList;

    public static final int INDEX = 0;
    public static final int SUMMARY = 1;
    public static final int NEWS = 2;


    public MarketAdapter(Context context, Object[] dataSet, int[] dataSetTypes) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
    }

    @Override
    public MarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == INDEX) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_indexs, parent, false);

            return new MarketHolder.IndexViewHolder(view);

        } else if (viewType == NEWS) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_profits, parent, false);
            return new MarketHolder.NewsViewHolder(view);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_profits, parent, false);
            return new MarketHolder.SummaryViewHolder(view);
        }

    }



    //Finished
    @Override
    public void onBindViewHolder(MarketHolder holder, int position) {
        if (holder.getItemViewType() == INDEX) {
            Ticker mIndex = (Ticker) mDataSet[position];
            ((MarketHolder.IndexViewHolder)holder).indexSymbol.setText(mIndex.getSymbol());
            ((MarketHolder.IndexViewHolder)holder).indexPrice.setText(mIndex.getPrice()+"");
            ((MarketHolder.IndexViewHolder)holder).indexChange.setText(mIndex.getChange()+mIndex.getPercentage());
        }
//        else if (viewHolder.getItemViewType() == NEWS) {
//            NewsViewHolder holder = (NewsViewHolder) viewHolder;
//            holder.headline.setText(mDataSet[position]);
//        }
//        else {
//            ScoreViewHolder holder = (ScoreViewHolder) viewHolder;
//            holder.score.setText(mDataSet[position]);
//        }
    }


    //Finished
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }


    //Finished
    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }






    public static class MarketHolder extends RecyclerView.ViewHolder{
        //View Declaration

        public MarketHolder(View itemView) {
            super(itemView);
        }

        public static class IndexViewHolder extends MarketHolder {
            private final TextView indexPrice;
            private final TextView indexChange;
            private final TextView indexSymbol;

            public IndexViewHolder(View v) {
                super(v);
                indexSymbol = (TextView) v.findViewById(R.id.indexSymbol);
                indexPrice = (TextView) v.findViewById(R.id.indexPrice);
                indexChange = (TextView)v.findViewById(R.id.indexChange);
            }
        }

        public static class SummaryViewHolder extends MarketHolder {

            public SummaryViewHolder(View v) {
                super(v);
                //this.score = (TextView) v.findViewById(R.id.score);
            }
        }

        public static class NewsViewHolder extends MarketHolder {

            public NewsViewHolder(View v) {
                super(v);
//                this.headline = (TextView) v.findViewById(R.id.headline);
//                this.read_more = (Button) v.findViewById(R.id.read_more);
            }
        }
    }
}