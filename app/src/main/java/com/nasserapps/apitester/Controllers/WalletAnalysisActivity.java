package com.nasserapps.apitester.Controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.Investment;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;

import java.util.ArrayList;

public class WalletAnalysisActivity extends AppCompatActivity {

    private ArrayList<Investment> mInvestmentsList;
    DataSource mDataSource;
    Wallet mWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDataSource = new DataSource(getApplicationContext());
        mWallet = mDataSource.getWallet();
        mInvestmentsList =new ArrayList<>();
        mInvestmentsList=(ArrayList)mWallet.getInvestmentList();

        PieChart pieChart = (PieChart)findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");


        ArrayList<Entry> yVals = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < mInvestmentsList.size(); i++) {
            yVals.add(new Entry((float) mInvestmentsList.get(i).getCurrentWorth(), i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < mInvestmentsList.size(); i++)
            xVals.add(mInvestmentsList.get(i).getStock().getName());

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
