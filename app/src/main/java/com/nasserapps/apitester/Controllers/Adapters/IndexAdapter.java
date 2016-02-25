package com.nasserapps.apitester.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexHolder> {

    private Context mContext;
    private List<Ticker> mIndexList;

    public IndexAdapter(Context context, List<Ticker> indexes) {
        mContext=context;
        mIndexList=indexes;
    }

    @Override
    public IndexHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_indexs,parent,false);
        return new IndexHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexHolder holder, int position) {
        Ticker index= mIndexList.get(position);
        holder.bindIndex(index);
    }

    @Override
    public int getItemCount() {
        return mIndexList.size();
    }

    public class IndexHolder extends RecyclerView.ViewHolder{
        //View Declaration
        private TextView mIndexSymbol;
        private TextView mIndexPrice;
        private TextView mIndexPercentage;
        private TextView mIndexChange;

        private Ticker mIndex;

        public IndexHolder(View itemView) {
            super(itemView);
            //Find view by ID

            mIndexSymbol = (TextView) itemView.findViewById(R.id.indexSymbol);
            mIndexPrice = (TextView) itemView.findViewById(R.id.indexPrice);
            mIndexPercentage = (TextView) itemView.findViewById(R.id.indexPercentage);
            mIndexChange = (TextView) itemView.findViewById(R.id.indexChange);
        }

        public void bindIndex(Ticker index){
            mIndex=index;
            mIndexSymbol.setText(mIndex.getSymbol());
            mIndexPrice.setText(mIndex.getPrice()+"");

            mIndexPrice.setTextColor(Tools.getTextColor(mContext, mIndex.getChange()));


            mIndexChange.setText("" + mIndex.getChange());
            mIndexPercentage.setText("(" + mIndex.getPercentage() + ")");

            mIndexChange.setTextColor(Tools.getTextColor(mContext, mIndex.getChange()));
            mIndexPercentage.setTextColor(Tools.getTextColor(mContext, mIndex.getChange()));
        }
    }
}