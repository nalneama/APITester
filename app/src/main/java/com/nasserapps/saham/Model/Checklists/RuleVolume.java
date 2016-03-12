package com.nasserapps.saham.Model.Checklists;


import com.nasserapps.saham.Model.Stock;

public class RuleVolume extends Rule {
    long mVCondition;
    int mVExpressionCase;
    public RuleVolume(long condition, int expression_case) {
        mVCondition=condition;
        mVExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Stock stock) {
        long volume = stock.getVolume();
        switch (mVExpressionCase){
            case 0:
                return volume > mVCondition;
            case 1:
                return volume > mVCondition || volume == mVCondition;
            case 2:
                return volume == mVCondition;
            case 4:
                return volume < mVCondition || volume == mVCondition;
            case 5:
                return volume < mVCondition ;
        }
        return false;
    }

    @Override
    public String getRuleStatement() {
        return "Volume "+Rule.mExpression_options.get(mVExpressionCase)+" "+mVCondition;
    }

}