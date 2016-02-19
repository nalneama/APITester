package com.nasserapps.apitester.Model.Checklists;

import com.nasserapps.apitester.Model.Ticker;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class Rule {

    //Criteria Chooser
    private static final ArrayList<String> mVariable_options =new ArrayList(Arrays.asList(new String[]{"PE Ratio", "Volume", "PBV Ratio"}));

    //Condition Chooser
    private static final ArrayList<String> mExpression_options =new ArrayList(Arrays.asList(new String[] {">","=","<"}));

        public boolean evaluate(Ticker stock){
            return false;
        }

        public String getRuleStatement(){ return "";}

    public static Rule getRule(String criteria, String condition, String value ){

        int criteria_case = mVariable_options.indexOf(criteria);
        int condition_case = mExpression_options.indexOf(condition);


            switch (criteria_case) {
                case 0:
                    return new RulePERatio(Double.parseDouble(value), condition_case);
                case 1:
                    return new RuleVolume(Long.parseLong(value), condition_case);
                case 2:
                    return new RulePBVRatio(Double.parseDouble(value), condition_case);
            }


            return new Rule() {
                @Override
                public boolean evaluate(Ticker stock) {
                    return false;
                }
            };
    }
}
//TODO add all rules
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