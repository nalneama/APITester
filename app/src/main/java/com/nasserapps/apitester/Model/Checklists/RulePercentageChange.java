package com.nasserapps.apitester.Model.Checklists;


import com.nasserapps.apitester.Model.Stock;

public class RulePercentageChange extends Rule {
    double mPriceCondition;
    int mPBVExpressionCase;
    public RulePercentageChange(double condition, int expression_case) {
        mPriceCondition =condition;
        mPBVExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Stock stock) {
        double price = Double.parseDouble(stock.getPercentage().substring(0, stock.getPercentage().length() - 1));
        switch (mPBVExpressionCase){
            case 0:
                return price > mPriceCondition;
            case 1:
                return price - mPriceCondition <0.001;
            case 2:
                return price < mPriceCondition;
        }
        return false;
    }

    @Override
    public String getRuleStatement() {
        return "Percentage Change "+Rule.mExpression_options.indexOf(mPBVExpressionCase)+" "+mPriceCondition;
    }
    //TODO add expressions to all rules
}