package com.nasserapps.apitester.AI.CustomNotificationStatement;


import com.nasserapps.apitester.Model.Ticker;

public class RuleVolume extends Rule {
    long mVCondition;
    int mVExpressionCase;
    public RuleVolume(long condition, int expression_case) {
        mVCondition=condition;
        mVExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Ticker stock) {
        long volume = stock.getVolume();
        switch (mVExpressionCase){
            case 0:
                return volume > mVCondition;
            case 1:
                return volume == mVCondition;
            case 2:
                return volume < mVCondition;
        }
        return false;
    }
}