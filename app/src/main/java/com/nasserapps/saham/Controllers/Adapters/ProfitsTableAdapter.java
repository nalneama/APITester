package com.nasserapps.saham.Controllers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.R;

import java.util.ArrayList;



public class ProfitsTableAdapter extends RecyclerView.Adapter<ProfitsTableAdapter.ProfitsTableHolder> {


    private ArrayList<Stock> mInvestments;

    public ProfitsTableAdapter(ArrayList<Stock> investmentsList) {
        mInvestments =investmentsList;
    }

    @Override
    public ProfitsTableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_profits,parent,false);
        return new ProfitsTableHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfitsTableHolder holder, int position) {

        Stock investment = mInvestments.get(position);
        holder.bindStock(investment,position);
    }

    @Override
    public int getItemCount() {
        return mInvestments.size();
    }

    public class ProfitsTableHolder extends RecyclerView.ViewHolder {

        private TextView mInvestmentNameView;
        private TextView mInvestmentAmount;
        private TextView mInvestmentROI;
        private TextView mInvestmentProfit;
        private Stock mInvestment;

        public ProfitsTableHolder(View itemView) {
            super(itemView);
            mInvestmentNameView = (TextView)itemView.findViewById(R.id.investmentName);
            mInvestmentAmount = (TextView) itemView.findViewById(R.id.investmentAmount);
            mInvestmentROI = (TextView) itemView.findViewById(R.id.investmentROI);
            mInvestmentProfit = (TextView) itemView.findViewById(R.id.investmentProfit);
        }

        public void bindStock(Stock investment,int position){
            mInvestment=investment;
            mInvestmentNameView.setText(investment.getName()+"");
            mInvestmentAmount.setText(String.format("%,.0f",investment.getQuantity()*mInvestment.getPrice()));
            mInvestmentROI.setText(investment.getPercentageChangeFromPurchasedPrice());
            mInvestmentProfit.setText(String.format("%,.0f",investment.getQuantity()*(mInvestment.getPrice()-investment.getPurchasedPrice())));
        }
    }

}

