package com.nasserapps.apitester.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Calculator;
import com.nasserapps.apitester.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalculatorFragment extends Fragment {

    EditText mCurrentPrice;
    EditText mAddedPrice;
    EditText mFinalPrice;
    EditText mCurrentQuantity;
    EditText mAddedQuantity;
    EditText mFinalQuantity;

    public CalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator, container, false);


        mCurrentPrice = (EditText)v.findViewById(R.id.currentPrice);
        mAddedPrice = (EditText)v.findViewById(R.id.requiredPrice);
        mFinalPrice = (EditText)v.findViewById(R.id.FinalPrice);
        mCurrentQuantity = (EditText)v.findViewById(R.id.currentQuantity);
        mAddedQuantity = (EditText)v.findViewById(R.id.requiredQuantity);
        mFinalQuantity = (EditText)v.findViewById(R.id.FinalQuantity);

        mCurrentPrice.setOnEditorActionListener(editor);
        mAddedPrice.setOnEditorActionListener(editor);
        mAddedQuantity.setOnEditorActionListener(editor);
        mCurrentQuantity.setOnEditorActionListener(editor);

        return v;
    }

    private TextView.OnEditorActionListener editor = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Add numbers for each view
            mFinalPrice.setText(Calculator.getAveragePrice(Double.parseDouble(mCurrentPrice.getText().toString()),
                    Double.parseDouble(mAddedPrice.getText().toString()),
                    Integer.parseInt(mCurrentQuantity.getText().toString()),
                    Integer.parseInt(mAddedQuantity.getText().toString()))+"");
            mFinalQuantity.setText((Integer.parseInt((mCurrentQuantity.getText().toString())+Integer.parseInt(mAddedQuantity.getText().toString())))+"");
            return false;
        }
    };
}
