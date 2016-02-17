package com.nasserapps.apitester.Controllers.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.nasserapps.apitester.Controllers.InProgress.EditInvestmentListActivity;
import com.nasserapps.apitester.Controllers.InProgress.RulesActivity;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    private ArrayList<Ticker> mInvestmentsList;
    Wallet mWallet;
    private CardView mWalletCard;
    private CardView mBlueCard;
    private TextView mCapitalView;
    private TextView mProfitView;
    PieChart pieChart;
    User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //1.1 Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        mWalletCard = (CardView) view.findViewById(R.id.walletCard);
        mCapitalView = (TextView) view.findViewById(R.id.capitalInvested);
        mProfitView = (TextView)view.findViewById(R.id.capitalProfit);




        pieChart = (PieChart)view.findViewById(R.id.pieChart);
        mBlueCard = (CardView) view.findViewById(R.id.blue_card);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser = User.getUser(getActivity());
        mWallet = mUser.getWallet();
        if(mWallet.getInvestments().size()>0) {
            //TODO get wallet from user
            mInvestmentsList =  mWallet.getInvestments();

            mWalletCard.setVisibility(View.VISIBLE);
            mBlueCard.setVisibility(View.GONE);
            //Hide the other card
            mCapitalView.setText(String.format(getString(R.string.Format_Capital),mWallet.getCurrentWorth()));
            mCapitalView.setTextColor(getResources().getColor(mWallet.getPriceColor()));

            //TODO change profit and loss statement
            mProfitView.setText("Profit: "+String.format(getString(R.string.Format_Capital),mWallet.getProfit()));

            //TODO add arrow direction
            //TODO add percentage change with color




            pieChart.setUsePercentValues(true);
            pieChart.setDescription("");


            ArrayList<Entry> yVals = new ArrayList<>();

            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
            for (int i = 0; i < mInvestmentsList.size(); i++) {
                yVals.add(new Entry((float) mInvestmentsList.get(i).getQuantity(), i));
            }

            ArrayList<String> xVals = new ArrayList<>();

            for (int i = 0; i < mInvestmentsList.size(); i++)
                xVals.add(mInvestmentsList.get(i).getName());

            PieDataSet dataSet = new PieDataSet(yVals, "");
            dataSet.setSliceSpace(2f);
            dataSet.setSelectionShift(5f);
            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(14f);
            data.setValueTextColor(Color.BLACK);
            pieChart.setData(data);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();

            Legend l = pieChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

        }
        else{
            mWalletCard.setVisibility(View.GONE);
            mBlueCard.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_investments, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_investment) {
            Intent i = new Intent(getActivity(), EditInvestmentListActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.add_rules) {
            Intent i = new Intent(getActivity(), RulesActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
