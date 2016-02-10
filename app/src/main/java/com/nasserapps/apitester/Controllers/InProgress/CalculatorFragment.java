package com.nasserapps.apitester.Controllers.InProgress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

//        mCurrentPrice.setOnEditorActionListener(editor);
//        mAddedPrice.setOnEditorActionListener(editor);
//        mAddedQuantity.setOnEditorActionListener(editor);
//        mCurrentQuantity.setOnEditorActionListener(editor);

        mCurrentPrice.addTextChangedListener(mTextWatcher);
        mAddedPrice.addTextChangedListener(mTextWatcher);
        mAddedQuantity.addTextChangedListener(mTextWatcher);
        mCurrentQuantity.addTextChangedListener(mTextWatcher);

        return v;
    }

    private TextView.OnEditorActionListener editor = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Add numbers for each view

            String currentPrice = mCurrentPrice.getText().toString();
            String addedPrice = mAddedPrice.getText().toString();
            String currentQuantity = mCurrentQuantity.getText().toString();
            String addedQuantity = mAddedQuantity.getText().toString();
            String finalQuantity = mFinalQuantity.getText().toString();
            String finalPrice = mFinalPrice.getText().toString();

            if(!currentPrice.isEmpty() && !addedPrice.isEmpty() && !currentQuantity.isEmpty() && !addedQuantity.isEmpty()){
            mFinalPrice.setText(String.format("%.2f",Calculator.getAveragePrice(Double.parseDouble(currentPrice),
                    Double.parseDouble(addedPrice),
                    Integer.parseInt(currentQuantity),
                    Integer.parseInt(addedQuantity))));
                int finalQuantityNumber=Integer.parseInt(currentQuantity) + Integer.parseInt(addedQuantity);
            mFinalQuantity.setText(String.format("%,d",finalQuantityNumber));
            }


            //TODO add reverse calculations for averages by putting action go

            return false;
        }
    };


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String currentPrice = mCurrentPrice.getText().toString();
            String addedPrice = mAddedPrice.getText().toString();
            String currentQuantity = mCurrentQuantity.getText().toString();
            String addedQuantity = mAddedQuantity.getText().toString();
            String finalQuantity = mFinalQuantity.getText().toString();
            String finalPrice = mFinalPrice.getText().toString();

            if(!currentPrice.isEmpty() && !addedPrice.isEmpty() && !currentQuantity.isEmpty() && !addedQuantity.isEmpty()){
                mFinalPrice.setText(String.format("%.2f",Calculator.getAveragePrice(Double.parseDouble(currentPrice),
                        Double.parseDouble(addedPrice),
                        Integer.parseInt(currentQuantity),
                        Integer.parseInt(addedQuantity))));
                int finalQuantityNumber=Integer.parseInt(currentQuantity) + Integer.parseInt(addedQuantity);
                mFinalQuantity.setText(String.format("%,d",finalQuantityNumber));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
