package com.nasserapps.apitester.Model.Checklists;


import com.nasserapps.apitester.Model.Stock;

public class RulePERatio extends Rule {
    double mPECondition;
    int mPEExpressionCase;
    public RulePERatio(double condition, int expression_case) {
        mPECondition=condition;
        mPEExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Stock stock) {
        double peRatio = stock.getPERatio();
        switch (mPEExpressionCase){
            case 0:
                return peRatio > mPECondition;
            case 1:
                return peRatio > mPECondition || (peRatio -mPECondition)<0.001;
            case 2:
                return (peRatio - mPECondition)<0.001;
            case 3:
                return (peRatio - mPECondition)<0.001 || peRatio <mPECondition;
            case 4:
                return peRatio < mPECondition;
        }
        return false;
    }


    @Override
    public String getRuleStatement() {
        return "PE Ratio "+Rule.mExpression_options.indexOf(mPEExpressionCase)+" "+mPECondition;
    }
}
