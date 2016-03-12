package com.nasserapps.saham.Controllers.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.nasserapps.saham.Controllers.Activities.InvestmentListActivity;
import com.nasserapps.saham.Controllers.Adapters.ProfitsTableAdapter;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.Tools;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.Model.Wallet;
import com.nasserapps.saham.R;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    private ArrayList<Stock> mInvestmentsList;
    Wallet mWallet;
    private CardView mWalletCard;
    private CardView mBlueCard;
    private TextView mCapitalView;
    private TextView mProfitView;
    PieChart mPieChart;
    User mUser;
    private RecyclerView mRecyclerView;
    private TextView mPercentageView;
    private ImageView mArrowView;

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
        mRecyclerView = (RecyclerView)view.findViewById(R.id.profits_recyclerview);
        mPercentageView = (TextView)view.findViewById(R.id.percentageCIChange);
        mArrowView = (ImageView)view.findViewById(R.id.InvestmentArrow);

        mPieChart = (PieChart)view.findViewById(R.id.pieChart);
        mBlueCard = (CardView) view.findViewById(R.id.blue_card);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser = User.getUser(getActivity());
        mWallet = mUser.getWallet();
        mWallet.setInvestmentList(mUser.getAllStocks());

        //If there is a wallet, then make things visible, else make the blue card visible
        if(mWallet.getInvestments().size()>0) {
            mInvestmentsList =  mWallet.getInvestments();

            mWalletCard.setVisibility(View.VISIBLE);
            mBlueCard.setVisibility(View.GONE);

            // Set the text for the wallet
            mCapitalView.setText(String.format(getString(R.string.Format_Capital), mWallet.getCurrentWorth()));
            mCapitalView.setTextColor(Tools.getTextColor(getActivity(), mWallet.getProfit()));
            mProfitView.setText(Tools.getProfitOrLossSting(mWallet.getProfit()) + ": " + String.format(getString(R.string.Format_Capital), mWallet.getProfit()));
            mArrowView.setImageDrawable(Tools.getArrowDirection(getActivity(), mWallet.getProfit()));
            mPercentageView.setText(String.format("%.2f%%", mWallet.getPercentageChange()));
            mPercentageView.setTextColor(Tools.getTextColor(getActivity(), mWallet.getProfit()));

            // Set the text inside the piechart
            //Image inside the pie chart
            Drawable image = Tools.getArrowDirection(getActivity(), mWallet.getProfit());
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(image,ImageSpan.ALIGN_BOTTOM);

            //Text inside the pie chart
            SpannableString spannableString = new SpannableString(" "+String.format("%.2f%%", mWallet.getPercentageChange()));
            spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            mPieChart.setCenterText(spannableString);
            mPieChart.setCenterTextSize(18f);
            mPieChart.setCenterTextColor(Tools.getTextColor(getActivity(), mWallet.getProfit()));

            //Set the pie chart area
            mPieChart.setUsePercentValues(true);
            mPieChart.setDescription("");
            mPieChart.highlightValues(null);
            mPieChart.setRotationEnabled(false);
            mPieChart.invalidate();
            mPieChart.setSelected(false);
            Legend l = mPieChart.getLegend();
            l.setEnabled(false);

            //Dataset (The pie chart values)
            ArrayList<Entry> yVals = new ArrayList<>();
            for (int i = 0; i < mInvestmentsList.size(); i++) {
                yVals.add(new Entry((float) mInvestmentsList.get(i).getQuantity(), i));
            }
            ArrayList<String> xVals = new ArrayList<>();
            for (int i = 0; i < mInvestmentsList.size(); i++)
                xVals.add(mInvestmentsList.get(i).getSymbol());

            PieDataSet dataSet = new PieDataSet(yVals, "");

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(getResources().getColor(R.color.amber));
            colors.add(getResources().getColor(R.color.green));
            colors.add(getResources().getColor(R.color.red));
            colors.add(getResources().getColor(R.color.blue));
            colors.add(getResources().getColor(R.color.purple));
            colors.add(getResources().getColor(R.color.teal));
            colors.add(getResources().getColor(R.color.deep_orange));
            dataSet.setSliceSpace(2f);
            dataSet.setSelectionShift(0f);
            dataSet.setColors(colors);

            //Data (The pie chart appearance)
            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLACK);
            mPieChart.setData(data);


            //TODO tablelayout to replace the recyclerview from http://www.journaldev.com/9531/android-scrollview-tablelayout-example-tutorial

            //Profits Table
            mRecyclerView.setAdapter(new ProfitsTableAdapter(mInvestmentsList));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        }
        else{
            mWalletCard.setVisibility(View.GONE);
            mBlueCard.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.wallet_protfolio, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_investment) {
            Intent i = new Intent(getActivity(), InvestmentListActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
