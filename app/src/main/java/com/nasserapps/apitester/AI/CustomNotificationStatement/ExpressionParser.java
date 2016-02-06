package com.nasserapps.apitester.AI.CustomNotificationStatement;

import com.nasserapps.apitester.Model.Ticker;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionParser {

    //Variable Chooser
    ArrayList<String> mVariable_options =new ArrayList(Arrays.asList(new String[]{"PE Ratio", "Volume"}));

    //Expression Chooser
    ArrayList<String> mExpression_options =new ArrayList(Arrays.asList(new String[] {">","=","<"}));

    public Rule getRule(String variable_option, String expression_option, String condition){
        int variable_case = mVariable_options.indexOf(variable_option);
        int expression_case = mExpression_options.indexOf(expression_option);
        switch (variable_case){
            case 0:
                return new RulePERatio(Double.parseDouble(condition), expression_case);
            case 1:
                return new RuleVolume(Long.parseLong(condition), expression_case);
        }
        return new Rule() {
            @Override
            public boolean evaluate(Ticker stock) {
                return false;
            }
        };
    }

}
