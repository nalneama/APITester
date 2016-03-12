package com.nasserapps.saham.Model.Checklists;

import com.nasserapps.saham.Model.Stock;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class Rule {


    //Criteria Chooser
    public static final ArrayList<String> mVariable_options =new ArrayList(Arrays.asList(new String[]{"Change" , "PE Ratio","Percentage", "Price", "Volume", "PBV Ratio"}));

    //Condition Chooser
    public static final ArrayList<String> mExpression_options =new ArrayList(Arrays.asList(new String[] {">",">=","=","<=","<"}));

        public boolean evaluate(Stock stock){
            return false;
        }

        public String getRuleStatement(){ return "";}

    public static Rule getRule(String criteria, String condition, String value ){

        int criteria_case = mVariable_options.indexOf(criteria);
        int condition_case = mExpression_options.indexOf(condition);


            switch (criteria_case) {
                case 0:
                    return new RuleChange(Double.parseDouble(value), condition_case);
                case 1:
                    return new RulePERatio(Double.parseDouble(value), condition_case);
                case 2:
                    return new RulePercentageChange(Double.parseDouble(value.substring(0, value.length() - 1)), condition_case);
                case 3:
                    return new RulePrice(Double.parseDouble(value), condition_case);
                case 4:
                    return new RuleVolume(Long.parseLong(value), condition_case);
                case 5:
                    return new RulePBVRatio(Double.parseDouble(value), condition_case);
            }


            return new Rule() {
                @Override
                public boolean evaluate(Stock stock) {
                    return false;
                }
            };
    }
}
/*Resources
Background Tasks
http://technology.jana.com/2014/10/28/periodic-background-tasks-in-android/comment-page-1/

API
http://resources.xignite.com/h/

Different Cards
http://arjunu.com/2015/10/android-recyclerview-with-different-cardviews/

PieChart
https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/PieChartActivity.java

Read and Copy Database from Asset Folder
http://www.geeks.gallery/read-and-copy-database-from-assets-folder/


 */