package com.nasserapps.apitester.AI.CustomNotificationStatement;

import com.nasserapps.apitester.Model.Ticker;

public class RulePERatio extends Rule {
    double mPECondition;
    int mPEExpressionCase;
    public RulePERatio(double condition, int expression_case) {
        mPECondition=condition;
        mPEExpressionCase=expression_case;
    }

    @Override
    public boolean evaluate(Ticker stock) {
        double peRatio = stock.getPERatio();
        switch (mPEExpressionCase){
            case 0:
                return peRatio >mPECondition;
            case 1:
                return peRatio -mPECondition<0.001;
            case 2:
                return peRatio <mPECondition;
        }
        return false;
    }
}
