package com.nasserapps.apitester.Model.Checklists;


import com.nasserapps.apitester.Model.Stock;

public class RulePrice extends Rule {
    double mPriceCondition;
    int mPBVExpressionCase;
    public RulePrice(double condition, int expression_case) {
        mPriceCondition =condition;
        mPBVExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Stock stock) {
        double price = stock.getPrice();
        switch (mPBVExpressionCase){
            case 0:
                return price > mPriceCondition;
            case 1:
                return (price - mPriceCondition) <0.001;
            case 2:
                return price < mPriceCondition;
        }
        return false;
    }

    @Override
    public String getRuleStatement() {
        return "Price "+ Rule.mExpression_options.get(mPBVExpressionCase)+" "+mPriceCondition;
    }
}