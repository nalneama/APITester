package com.nasserapps.apitester;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nasser on 10/26/15.
 */
public class exchangeTime {

    public static final String inputFormat = "HH:mm";
    private static final String mOpenTimeString = "09:30";
    private static final String mCloseTimeString = "13:15";

    public Date mCurrentTime;
    private String mCurrentTimeString;
    SimpleDateFormat TimeConverter;

    public exchangeTime(){

        Calendar now = Calendar.getInstance();

        TimeConverter = new SimpleDateFormat(inputFormat, Locale.US);

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        mCurrentTimeString = hour + ":" + minute;
        //Testing code, test by changing to 09:00, 09:31, 12:00, 13:14, 13:17
        //mCurrentTime = parseDate ("13:14");
        mCurrentTime = parseDate(mCurrentTimeString);
    }

    public boolean isInTheExchangePeriod(){


        Date mOpenTime;
        Date mCloseTime;

        mOpenTime = parseDate(mOpenTimeString);
        mCloseTime = parseDate(mCloseTimeString);
        boolean logic1= mCurrentTime.after(mOpenTime);
        boolean logic2 = mCurrentTime.before(mCloseTime);
        boolean logic3 = (!mCurrentTime.toString().contains("Fri")) ||!mCurrentTime.toString().contains("Sat");

        if ( (logic1 && logic2) && logic3) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getCurrentTimeString() {
        return mCurrentTimeString;
    }

    public static String getmOpenTimeString() {
        return mOpenTimeString;
    }

    public static String getmCloseTimeString() {
        return mCloseTimeString;
    }

    private Date parseDate(String date) {

        try {
            return TimeConverter.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
