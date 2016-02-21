package com.nasserapps.apitester.Model;

import android.content.Context;

import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Checklists.Rule;
import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Database.JSONParser;
import com.nasserapps.apitester.Tools;

import org.json.JSONException;

import java.util.ArrayList;

public class User {

    private static User sUser;
    private Wallet mWallet;
    private UserData mUserData;
    private DataSource mDataSource;
    private ArrayList<Checklist> mChecklists;


    //Constructors
    public static User getUser(Context context){
        if(sUser == null){
            sUser = new User(context);
        }
        return sUser;
    }

    private User(Context context) {
        //Wallet includes initializing the Wallet Card
        mWallet= new Wallet();

        //UserData includes initializing the application settings
        mUserData = new UserData(context);

        //DataSource includes initializing the stock and index lists
        mDataSource = new DataSource(context);

        mChecklists = new ArrayList<>();
        //mChecklists = mUserData.getChecklist();

    }



    //Get User Variables
    public ArrayList<Ticker> getAllStocks(){
        ArrayList<Ticker> allStocks = new ArrayList<>();
        allStocks.addAll(mDataSource.getStocks().values());
        //return sort(allStocks, mUserData.getSortPreference());
        return Tools.sort(allStocks,"A-Z");
    }

    public ArrayList<Ticker> getWatchList(){
        ArrayList<Ticker> watchlist = new ArrayList<>();
        for(Ticker ticker:mDataSource.getStocks().values()){
            if (ticker.isInWatchList()){
                watchlist.add(ticker);
            }
        }
        return Tools.sort(watchlist,mUserData.getSortPreference());
    }

    public void updateStocksData(String jsonData) throws JSONException{
        JSONParser jsonParser = new JSONParser(jsonData,getAllStocks());
        ArrayList<Ticker> stocks = jsonParser.getStocks();
        for (Ticker stock:stocks){
            mDataSource.updateStock(stock);
        }
    }

    public Wallet getWallet(){
        mWallet.setInvestmentList(getAllStocks());
        return mWallet;
    }

    public String getAPIURL() {
        //Initiate SharedPreferences
        // https://www.xignite.com/Register?ReturnURL=%2fproduct%2fforex%2fapi%2fListCurrencies%2f

        String APICode="";
        for(Ticker stock:getAllStocks()){
            APICode=APICode+stock.getAPICode()+"+";
        }

        String stockURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%3D%22"
                + APICode
                + "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        return stockURL;
    }

    //Get User Data
    public UserData getUserData() {
        return mUserData;
    }


    public ArrayList<Checklist> getChecklists() {
        //TODO get checklists from database
        mChecklists.add(new Checklist("Buffet Checklist"));

        // Adding rules
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(Rule.getRule("PE Ratio", "<", "15.0"));
        rules.add(Rule.getRule("PBV Ratio", "<", "1.5"));
        mChecklists.get(0).setRules(rules);
        return mChecklists;
        //return mChecklists;
    }

    public void setChecklists(ArrayList<Checklist> checklists) {
        mUserData.setChecklist(checklists);
    }
}
