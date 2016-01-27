package com.nasserapps.apitester.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.Investment;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;

public class EditInvestmentListActivity extends AppCompatActivity {

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<Investment> mAllInvestmentsList;
    private ArrayList<Ticker> mStockWatchList;
    private ArrayList<Investment> mInvestmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_investment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataSource mDataSource = new DataSource(getApplicationContext());
        Wallet mWallet = mDataSource.getWallet();
        mStockWatchList =mWallet.getWatchList();

        Log.e("zxc",mStockWatchList.isEmpty()+"");
        mAllInvestmentsList =new ArrayList<>();
        for(Ticker stock:mStockWatchList){
            mAllInvestmentsList.add(new Investment(stock, 0, 0));
        }

        Log.e("zxc", mAllInvestmentsList.get(0).getStock().getName()+"");

        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_investment_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new InvestmentListAdapter(mAllInvestmentsList));
    }

    private class InvestmentListAdapter extends RecyclerView.Adapter<InvestmentListHolder> {

        private ArrayList<Investment> mInvestments;

        public InvestmentListAdapter(ArrayList<Investment> investmentsList) {
            mInvestments =investmentsList;
        }

        @Override
        public InvestmentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_edit_wallet,parent,false);
            return new InvestmentListHolder(view);
        }

        @Override
        public void onBindViewHolder(InvestmentListHolder holder, int position) {
            Investment investment = mInvestments.get(position);
            holder.bindStock(investment);
        }

        @Override
        public int getItemCount() {
            return mInvestments.size();
        }
    }

    private class InvestmentListHolder extends RecyclerView.ViewHolder {

        private TextView mInvestmentNameView;
        private CheckBox mInvestmentCheckbox;
        private EditText mInvestmentQuantity;
        private EditText mPurchasedPrice;

        public InvestmentListHolder(View itemView) {
            super(itemView);
            mInvestmentNameView = (TextView)itemView.findViewById(R.id.edit_investment_name);
            mInvestmentCheckbox = (CheckBox) itemView.findViewById(R.id.edit_investment_checkbox);
            mInvestmentQuantity = (EditText) itemView.findViewById(R.id.investmentQuantity);
            mPurchasedPrice = (EditText) itemView.findViewById(R.id.investmentPrice);
        }

        public void bindStock(final Investment investment){
            mInvestmentNameView.setText(investment.getStock().getSymbol()+"");
            mInvestmentQuantity.setText(investment.getQuantity()+"");
            mPurchasedPrice.setText(investment.getPurchasedPrice()+"");
            mPurchasedPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    investment.setPurchasedPrice(Double.parseDouble(v.getText()+""));
                    return true;
                }
            });
            mInvestmentCheckbox.setChecked(investment.getQuantity()>0);
            mInvestmentCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mInvestmentCheckbox.isChecked()){
                        //mStocksList.add(mStockName);
                        mInvestmentQuantity.setFocusable(true);
                        mInvestmentQuantity.setFocusableInTouchMode(true);
                        mInvestmentQuantity.setClickable(true);
                        mPurchasedPrice.setFocusable(true);
                        mPurchasedPrice.setFocusableInTouchMode(true);
                        mPurchasedPrice.setClickable(true);
                    }
                    else {
                        //mStocksList.remove(mStockName);
                        mInvestmentQuantity.setFocusable(false);
                        mInvestmentQuantity.setFocusableInTouchMode(false);
                        mInvestmentQuantity.setClickable(false);
                        mPurchasedPrice.setFocusable(false);
                        mPurchasedPrice.setFocusableInTouchMode(false);
                        mPurchasedPrice.setClickable(false);
                    }
                }
            });
        }
    }

}
