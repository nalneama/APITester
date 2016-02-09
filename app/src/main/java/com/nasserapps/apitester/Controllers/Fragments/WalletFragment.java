package com.nasserapps.apitester.Controllers.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class WalletFragment extends Fragment {

    private ArrayList<Investment> mInvestmentsList;
    DataSource mDataSource;
    Wallet mWallet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1.1 Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        mDataSource = new DataSource(getActivity().getApplicationContext());
        mWallet = mDataSource.getWallet();
        mInvestmentsList =new ArrayList<>();
        mInvestmentsList=(ArrayList)mWallet.getInvestmentList();

        PieChart pieChart = (PieChart)view.findViewById(R.id.pieChart);
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

        return view;
    }
}
