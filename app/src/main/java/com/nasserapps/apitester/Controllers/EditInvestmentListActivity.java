package com.nasserapps.apitester.Controllers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private ArrayList<Investment> mInvestmentsList;
    private ArrayList<Ticker> mStockWatchList;
    EditText dInvestmentQuantity;
    EditText dPurchasedPrice;
    AutoCompleteTextView dAutoCompleteTextView;
    private int mQuantity;
    DataSource mDataSource;
    Wallet mWallet;
    private double mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_investment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataSource = new DataSource(getApplicationContext());
        mWallet = mDataSource.getWallet();
        mStockWatchList =mWallet.getWatchList();

        Log.e("zxc",mStockWatchList.isEmpty()+"");
        mInvestmentsList =new ArrayList<>();
        mInvestmentsList=(ArrayList)mWallet.getInvestmentList();
        //for(Ticker stock:mStockWatchList){
          //  mInvestmentsList.add(new Investment(stock, 0, 0));
//        }

        //Log.e("zxc", mInvestmentsList.get(0).getStock().getName()+"");

        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_investment_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new InvestmentListAdapter(mInvestmentsList));
        FloatingActionButton mFAB = (FloatingActionButton)findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add an Investment")
                        .setView(R.layout.dialog_add_investments)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPrice = Double.parseDouble(dPurchasedPrice.getText().toString());
                                mQuantity = Integer.parseInt(dInvestmentQuantity.getText().toString());
                                String stock = dAutoCompleteTextView.getText().toString();
                                Investment i = new Investment(new Ticker("Empty"), 0, 0);
                                for (Ticker s : mStockWatchList) {
                                    if (stock.contains(s.getSymbol())) {
                                        i = new Investment(s, mPrice, mQuantity);
                                    }
                                }
                                if (!i.getStock().getAPICode().contains("Empty")) {
                                    mInvestmentsList.add(i);
                                    mWallet.setInvestmentList(mInvestmentsList);
                                    mDataSource.saveWallet(mWallet);
                                }
                                //if not correct show snakbar
                                //Snackbar.make(mEditStocksRecyclerView, "hahaha", Snackbar.LENGTH_LONG).show();
                                mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mInvestmentsList), false);
                            }
                        });
                builder.create();


                AlertDialog alertDialog = builder.show();
                dAutoCompleteTextView = (AutoCompleteTextView)alertDialog.findViewById(R.id.autoCompleteTextView);
                dInvestmentQuantity = (EditText) alertDialog.findViewById(R.id.investmentQuantity);
                dPurchasedPrice = (EditText) alertDialog.findViewById(R.id.investmentPrice);
                String[] companies = getResources().getStringArray(R.array.companies_array);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, companies);
                dAutoCompleteTextView.setAdapter(adapter);


            }
        });
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
            holder.bindStock(investment,position);
        }

        @Override
        public int getItemCount() {
            return mInvestments.size();
        }
    }

    private class InvestmentListHolder extends RecyclerView.ViewHolder {

        private TextView mInvestmentNameView;
        private TextView mInvestmentQuantity;
        private TextView mPurchasedPrice;
        private int mPosition;
        private Investment mInvestment;

        public InvestmentListHolder(View itemView) {
            super(itemView);
            mInvestmentNameView = (TextView)itemView.findViewById(R.id.edit_investment_name);
            mInvestmentQuantity = (TextView) itemView.findViewById(R.id.investmentQuantity);
            mPurchasedPrice = (TextView) itemView.findViewById(R.id.investmentPrice);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Edit the Investment")
                            .setView(R.layout.dialog_add_investments)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPrice = Double.parseDouble(dPurchasedPrice.getText().toString());
                                    mQuantity = Integer.parseInt(dInvestmentQuantity.getText().toString());
                                    mInvestment.setQuantity(mQuantity);
                                    mInvestment.setPurchasedPrice(mPrice);
                                    //if not correct show snakbar
                                    mInvestmentsList.set(mPosition,mInvestment);
                                    mWallet.setInvestmentList(mInvestmentsList);
                                    mDataSource.saveWallet(mWallet);
                                    mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mInvestmentsList),false);
                                }
                            })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mInvestmentsList.remove(mPosition);
                            mWallet.setInvestmentList(mInvestmentsList);
                            mDataSource.saveWallet(mWallet);
                            mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mInvestmentsList),false);
                        }
                    });
                    builder.create();


                    AlertDialog alertDialog = builder.show();
                    dAutoCompleteTextView = (AutoCompleteTextView)alertDialog.findViewById(R.id.autoCompleteTextView);
                    dInvestmentQuantity = (EditText) alertDialog.findViewById(R.id.investmentQuantity);
                    dPurchasedPrice = (EditText) alertDialog.findViewById(R.id.investmentPrice);
                    dAutoCompleteTextView.setText(mInvestmentNameView.getText());
                    dInvestmentQuantity.setText(mInvestmentQuantity.getText());
                    dPurchasedPrice.setText(mPurchasedPrice.getText());
                    String[] companies = getResources().getStringArray(R.array.companies_array);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, companies);
                    dAutoCompleteTextView.setAdapter(adapter);
                    return false;
                }
            });
        }

        public void bindStock(Investment investment,int position){
            mInvestment=investment;
            mPosition=position;
            mInvestmentNameView.setText(investment.getStock().getSymbol()+"");
            mInvestmentQuantity.setText(investment.getQuantity() + "");
            mPurchasedPrice.setText(investment.getPurchasedPrice() + "");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_investments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.reset_investment_list) {
            mInvestmentsList.clear();
            mWallet.setInvestmentList(mInvestmentsList);
            mDataSource.saveWallet(mWallet);
            mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mInvestmentsList),false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
