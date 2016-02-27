package com.nasserapps.apitester.Controllers.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

import java.util.ArrayList;
import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketHolder> {

    private final Object[] mDataSet;
    private final int[] mDataSetTypes;
    private Context mContext;

    public static final int INDEX = 0;
    public static final int SUMMARY = 1;
    public static final int NEWS = 2;


    public MarketAdapter(Context context, Object[] dataSet, int[] dataSetTypes) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
        mContext=context;
    }

    @Override
    public MarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == INDEX) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_market_index, parent, false);

            return new MarketHolder.IndexViewHolder(view);

        } else if (viewType == NEWS) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_profits, parent, false);
            return new MarketHolder.NewsViewHolder(view);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_top_stocks, parent, false);
            return new MarketHolder.SummaryViewHolder(view);
        }

    }



    //Finished
    @Override
    public void onBindViewHolder(MarketHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == INDEX) {
            Ticker mIndex = (Ticker) mDataSet[position];
            //((MarketHolder.IndexViewHolder)holder).indexSymbol.setText(mIndex.getSymbol());
            MarketHolder.IndexViewHolder holder = ((MarketHolder.IndexViewHolder)viewHolder);
            holder.indexPrice.setText(mIndex.getPrice()+"");
            holder.indexChange.setText(mIndex.getChange()+"("+mIndex.getPercentage()+")");
            holder.indexChange.setTextColor(Tools.getTextColor(mContext,mIndex.getChange()));
        }
        else if (viewHolder.getItemViewType() == NEWS) {
//            NewsViewHolder holder = (NewsViewHolder) viewHolder;
//            holder.headline.setText(mDataSet[position]);
        }
        else {
            ArrayList<Ticker> gainers = (ArrayList<Ticker>)  mDataSet[position];
            MarketHolder.SummaryViewHolder holder = ((MarketHolder.SummaryViewHolder) viewHolder);
            if(position==2){
                holder.mChartTitle.setText("Top Losers");
            }
            holder.mChart.setDrawBarShadow(false);
            holder.mChart.setDrawValueAboveBar(true);
            holder.mChart.setExtraBottomOffset(16f);
            holder.mChart.setDescription("");
            holder.mChart.setScaleXEnabled(false);
            holder.mChart.setScaleYEnabled(false);
            holder.mChart.setPinchZoom(false);
            holder.mChart.setPressed(false);
            holder.mChart.setDoubleTapToZoomEnabled(false);
            holder.mChart.setDrawGridBackground(false);

            XAxis xAxis = holder.mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setSpaceBetweenLabels(2);
            xAxis.setTextColor(Color.LTGRAY);
            xAxis.setTextSize(11f);

            YAxis left = holder.mChart.getAxisLeft();
            left.setDrawLabels(false);
            left.setStartAtZero(false);
            left.setSpaceTop(25f);
            left.setSpaceBottom(25f);
            left.setDrawAxisLine(false);
            left.setDrawGridLines(false);
            left.setDrawZeroLine(true); // draw a zero line
            left.setZeroLineColor(Color.GRAY);
            left.setZeroLineWidth(0.7f);
            holder.mChart.getAxisRight().setEnabled(false);
            holder.mChart.getLegend().setEnabled(false);

            // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT
            List<Data> data = new ArrayList<>();
            data.add(new Data(0, Float.parseFloat(gainers.get(0).getPercentage().replace("%", "")), gainers.get(0).getSymbol()));
            data.add(new Data(1, Float.parseFloat(gainers.get(1).getPercentage().replace("%","")), gainers.get(1).getSymbol()));
            data.add(new Data(2, Float.parseFloat(gainers.get(2).getPercentage().replace("%","")), gainers.get(2).getSymbol()));
            data.add(new Data(3, Float.parseFloat(gainers.get(3).getPercentage().replace("%","")), gainers.get(3).getSymbol()));
            data.add(new Data(4, Float.parseFloat(gainers.get(4).getPercentage().replace("%","")), gainers.get(4).getSymbol()));

            setData(data, holder.mChart);
        }
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

            private final BarChart mChart;
            private final TextView mChartTitle;

            public SummaryViewHolder(View v) {
                super(v);
                mChart = (BarChart) v.findViewById(R.id.chart1);
                mChartTitle = (TextView)v.findViewById(R.id.chartTitle);
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


    private void setData(List<Data> dataList, BarChart barChart) {

        BarChart mChart = barChart;
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        String[] dates = new String[dataList.size()];
        List<Integer> colors = new ArrayList<Integer>();

        for (int i = 0; i < dataList.size(); i++) {

            Data d = dataList.get(i);
            BarEntry entry = new BarEntry(d.yValue, d.xIndex);
            values.add(entry);

            dates[i] = dataList.get(i).xAxisValue;

            // specific colors
            if (d.yValue >= 0)
                colors.add(mContext.getResources().getColor(R.color.green));
            else
                colors.add(mContext.getResources().getColor(R.color.red));
        }

        BarDataSet set = new BarDataSet(values, "Values");
        set.setBarSpacePercent(40f);
        set.setColors(colors);
        set.setValueTextColors(colors);

        BarData data = new BarData(dates, set);
        data.setValueTextSize(13f);
        data.setValueFormatter(new PercentFormatter());
        data.setHighlightEnabled(false);

        mChart.setData(data);
        mChart.invalidate();
    }


    private class Data {

        public String xAxisValue;
        public float yValue;
        public int xIndex;

        public Data(int xIndex, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xIndex = xIndex;
        }
    }

}