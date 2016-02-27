package com.nasserapps.apitester.Controllers.InProgress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;

import java.util.ArrayList;

public class InvestmentListActivity extends AppCompatActivity {

    private RecyclerView mEditStocksRecyclerView ;
    private ArrayList<Ticker> mInvestmentsList;
    EditText dInvestmentQuantity;
    EditText dPurchasedPrice;
    private Spinner mStockNameChooser;
    private int mQuantity;
    private User mUser;
    private DataSource mDataSource;
    private double mPrice;
    Ticker ticker;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_investment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDataSource = new DataSource(this);
        mUser = User.getUser(this);
        mInvestmentsList = mUser.getWallet().getInvestments();


        mEditStocksRecyclerView = (RecyclerView) findViewById(R.id.edit_investment_list_recyclerview);
        mEditStocksRecyclerView.setHasFixedSize(true);
        mEditStocksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEditStocksRecyclerView.setAdapter(new InvestmentListAdapter(mInvestmentsList));
        mEditStocksRecyclerView.setItemAnimator(new DefaultItemAnimator());
        FloatingActionButton mFAB = (FloatingActionButton)findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdditionDialog("add",0);


            }
        });
        mContext = mFAB.getContext();
    }








    private void showAdditionDialog(String operation, final int positionClicked) {
        mInvestmentsList = mUser.getWallet().getInvestments();
        String title= getString(R.string.dialog_add_investments_title);
        String positiveButton=getString(R.string.dialog_add_investments_add_button);

        if(!operation.equalsIgnoreCase("add")){
            title="Edit the Investment";
            positiveButton="Update";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title)
                .setView(R.layout.dialog_add_investments)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPrice = Double.parseDouble(dPurchasedPrice.getText().toString());
                        mQuantity = Integer.parseInt(dInvestmentQuantity.getText().toString());
                        ticker.setPurchasedPrice(mPrice);
                        ticker.setQuantity(mQuantity);
                        ticker.setInInvestments(true);
                        mDataSource.updateStock(ticker);
                        mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mUser.getWallet().getInvestments()), false);
                        //TODO show snackbar to undo the last addition

                        //set action to show another dialog to update or add ticker if it is already existing
                    }
                });
        if(!operation.equalsIgnoreCase("add")){
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Ticker ticker = mUser.getWallet().getInvestments().get(positionClicked);
                    ticker.setInInvestments(false);
                    ticker.setQuantity(0);
                    ticker.setPurchasedPrice(0);
                    mDataSource.updateStock(ticker);
                    mInvestmentsList = mUser.getWallet().getInvestments();
                    mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mInvestmentsList), false);
                }
            });
        }
        builder.create();
        AlertDialog alertDialog = builder.show();


        dInvestmentQuantity = (EditText) alertDialog.findViewById(R.id.investmentQuantity);
        dPurchasedPrice = (EditText) alertDialog.findViewById(R.id.investmentPrice);

        mStockNameChooser = (Spinner) alertDialog.findViewById(R.id.stockName);
        ArrayList<String> names = new ArrayList<>();
        for(Ticker ticker:mUser.getAllStocks()){
            names.add(ticker.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_choose,names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStockNameChooser.setAdapter(adapter);
        mStockNameChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ticker = mUser.getAllStocks().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(!operation.equalsIgnoreCase("add")){
            dInvestmentQuantity.setText(mInvestmentsList.get(positionClicked).getQuantity()+"");
            dPurchasedPrice.setText(mInvestmentsList.get(positionClicked).getPurchasedPrice()+"");
            String tickerName =mInvestmentsList.get(positionClicked).getName();
            mStockNameChooser.setSelection(adapter.getPosition(tickerName));
            //TODO fix update button
        }
    }















    private class InvestmentListAdapter extends RecyclerView.Adapter<InvestmentListHolder> {

        private ArrayList<Ticker> mInvestments;

        public InvestmentListAdapter(ArrayList<Ticker> investmentsList) {
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
            Ticker investment = mInvestments.get(position);
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
        private Ticker mInvestment;

        public InvestmentListHolder(View itemView) {
            super(itemView);
            mInvestmentNameView = (TextView)itemView.findViewById(R.id.edit_investment_name);
            mInvestmentQuantity = (TextView) itemView.findViewById(R.id.investmentQuantity);
            mPurchasedPrice = (TextView) itemView.findViewById(R.id.investmentPrice);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                showAdditionDialog("update",getAdapterPosition());
                return false;
            }
        });
        }

        public void bindStock(Ticker investment,int position){
            mInvestment=investment;
            mInvestmentNameView.setText(mInvestment.getName()+"");
            mInvestmentQuantity.setText(mInvestment.getQuantity() + "");
            mPurchasedPrice.setText(mInvestment.getPurchasedPrice() + "");
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
            //TODO  add confirmation dialog for the delete
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete all investments?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (Ticker ticker : mUser.getWallet().getInvestments()) {
                                ticker.setInInvestments(false);
                                ticker.setQuantity(0);
                                ticker.setPurchasedPrice(0);
                                mDataSource.updateStock(ticker);
                            }
                            mEditStocksRecyclerView.swapAdapter(new InvestmentListAdapter(mUser.getWallet().getInvestments()), false);
                        }
                    })
                    .setNegativeButton("Cancel",null);
            builder.create().show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
