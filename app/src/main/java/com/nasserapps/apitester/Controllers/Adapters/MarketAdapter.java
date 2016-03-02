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
import com.nasserapps.apitester.Model.Commodity;
import com.nasserapps.apitester.Model.Index;
import com.nasserapps.apitester.Model.Market;
import com.nasserapps.apitester.Model.Stock;
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
    public static final int COMMODITY = 2;
    public static final int MARKET=3;

    public MarketAdapter(Context context, Object[] dataSet, int[] dataSetTypes) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
        mContext=context;
    }

    @Override
    public MarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == MARKET) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_market, parent, false);
            return new MarketHolder.MarketViewHolder(view);

        } else if (viewType == COMMODITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_commodity, parent, false);
            return new MarketHolder.CommodityViewHolder(view);

        } else {
            if(viewType == SUMMARY){}
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_top_stocks, parent, false);
            return new MarketHolder.SummaryViewHolder(view);
        }

    }



    //Finished
    @Override
    public void onBindViewHolder(MarketHolder viewHolder, int position) {

        if (viewHolder.getItemViewType() == MARKET) {
            //BIND INDEX
            //((MarketHolder.IndexViewHolder)holder).indexSymbol.setText(mIndex.getSymbol());
            MarketHolder.MarketViewHolder holder = ((MarketHolder.MarketViewHolder)viewHolder);
            Market marketData = (Market) mDataSet[position];

            Commodity mCommodity = marketData.getCommodity();
            holder.commodityPrice.setText(mCommodity.getPrice() + "");
            holder.commodityChange.setText(mCommodity.getChange() + " (" + mCommodity.getPercentage() + ")");
            holder.commodityChange.setTextColor(Tools.getTextColor(mContext, mCommodity.getChange()));
            holder.commodityPrice.setTextColor(Tools.getTextColor(mContext, mCommodity.getChange()));

            Index index = marketData.getIndex();
            holder.indexUpAndDown.setText(String.format("%d%n%d%n%d", index.getUps(), index.getNCs(),index.getDowns()));
            holder.indexName.setText(index.getName());
            holder.indexValue.setText(index.getValue()+"");
            holder.indexChange.setText(index.getChange() + " (" + index.getPercentage() + ")");

        }
        else if (viewHolder.getItemViewType() == COMMODITY) {
            //BIND COMMODITY

        }
        else {
            //Bind SUMMARY
            ArrayList<Stock> gainers = (ArrayList<Stock>)  mDataSet[position];
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

            xAxis.setLabelsToSkip(0);
            xAxis.setTextColor(mContext.getResources().getColor(R.color.grey500));
            xAxis.setTextSize(8f);

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

            data.add(new Data(0, Float.parseFloat(gainers.get(0).getPercentage().replace("%", "")), gainers.get(0).getName()));
            data.add(new Data(1, Float.parseFloat(gainers.get(1).getPercentage().replace("%","")), gainers.get(1).getName()));
            data.add(new Data(2, Float.parseFloat(gainers.get(2).getPercentage().replace("%","")), gainers.get(2).getName()));
            data.add(new Data(3, Float.parseFloat(gainers.get(3).getPercentage().replace("%","")), gainers.get(3).getName()));
            data.add(new Data(4, Float.parseFloat(gainers.get(4).getPercentage().replace("%","")), gainers.get(4).getName()));

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





        public static class SummaryViewHolder extends MarketHolder {

            private final BarChart mChart;
            private final TextView mChartTitle;

            public SummaryViewHolder(View v) {
                super(v);
                mChart = (BarChart) v.findViewById(R.id.chart1);
                mChartTitle = (TextView)v.findViewById(R.id.chartTitle);
            }
        }





        public static class CommodityViewHolder extends MarketHolder {


            public CommodityViewHolder(View v) {
                super(v);


//                this.headline = (TextView) v.findViewById(R.id.headline);
//                this.read_more = (Button) v.findViewById(R.id.read_more);
            }
        }
    }








    //Helper methods for the pie charts
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