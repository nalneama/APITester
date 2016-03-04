package com.nasserapps.apitester.Model.Checklists;


import com.nasserapps.apitester.Model.Stock;

public class RulePBVRatio extends Rule {
    double mPBVCondition;
    int mPBVExpressionCase;
    public RulePBVRatio(double condition, int expression_case) {
        mPBVCondition=condition;
        mPBVExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Stock stock) {
        double pbvRatio = stock.getPBV();
        switch (mPBVExpressionCase){
            case 0:
                return pbvRatio >mPBVCondition;
            case 1:
                return pbvRatio >mPBVCondition || (pbvRatio -mPBVCondition<0.001);
            case 2:
                return pbvRatio -mPBVCondition<0.001;
            case 3:
                return pbvRatio <mPBVCondition || (pbvRatio -mPBVCondition<0.001);
            case 4:
                return pbvRatio <mPBVCondition;
        }
        return false;
    }

    @Override
    public String getRuleStatement() {
        return "Price to Book Value "+Rule.mExpression_options.indexOf(mPBVExpressionCase)+" "+mPBVCondition;
    }
}