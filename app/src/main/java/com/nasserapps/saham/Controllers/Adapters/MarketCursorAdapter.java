package com.nasserapps.saham.Controllers.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nasserapps.saham.R;


public class MarketCursorAdapter extends RecyclerView.Adapter<MarketCursorAdapter.MarketHolder> {

    Cursor mDataSet;
    private Context mContext;
    private CursorAdapter mCursorAdapter;


    public MarketCursorAdapter(Context context, Cursor cursor) {
        mDataSet = cursor;
        mContext=context;
        mCursorAdapter = new CursorAdapter(mContext,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                //Inflate view here

                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.card_commodity, parent, false);
                    return view;

                }


            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                //Binding operation

                    //BIND INDEX
                    //((MarketHolder.IndexViewHolder)holder).indexSymbol.setText(mIndex.getSymbol());
                    MarketHolder.MarketViewHolder holder = new MarketHolder.MarketViewHolder(view);
//                    Market marketData = (Market) mDataSet[0];
//
//                    Commodity mCommodity = marketData.getCommodity();
//                    holder.commodityPrice.setText(mCommodity.getPrice() + "");
//                    holder.commodityChange.setText(mCommodity.getChange() + " (" + mCommodity.getPercentage() + ")");
//                    holder.commodityChange.setTextColor(Tools.getTextColor(mContext, mCommodity.getChange()));
//                    holder.commodityPrice.setTextColor(Tools.getTextColor(mContext, mCommodity.getChange()));


            }
        };
    }

    public void swapCursor(Cursor cursor){
        mCursorAdapter.swapCursor(cursor);
    }



    @Override
    public MarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        // Passing the inflater job to the cursor-adapter
        view = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);

            return new MarketHolder.MarketViewHolder(view);

    }



    //Finished
    @Override
    public void onBindViewHolder(MarketHolder viewHolder, int position) {
        //Passing binding operations to cursor loader
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(viewHolder.itemView,mContext,mCursorAdapter.getCursor());


    }


    //Finished
    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }






    public static class MarketHolder extends RecyclerView.ViewHolder{
        //View Declaration

        public MarketHolder(View itemView) {
            super(itemView);
        }






        public static class MarketViewHolder extends MarketHolder {

            private final TextView indexUpAndDown;
            private final TextView commodityPrice;
            private final TextView commodityChange;
            private final TextView indexValue;
            private final TextView indexChange;
            private final TextView indexName;

            public MarketViewHolder(View v) {
                super(v);
                indexUpAndDown = (TextView)v.findViewById(R.id.indexCompaniesUpAndDown);
                indexValue = (TextView)v.findViewById(R.id.indexValue);
                indexChange = (TextView)v.findViewById(R.id.indexChange);
                indexName = (TextView)v.findViewById(R.id.indexName);

                commodityPrice = (TextView) v.findViewById(R.id.indexPrice);
                commodityChange = (TextView)v.findViewById(R.id.indexChange);
            }
        }

    }











}