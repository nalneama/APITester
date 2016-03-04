package com.nasserapps.apitester.Model;

import android.content.Context;

import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Checklists.Rule;
import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Database.JSONParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<Stock> getAllStocks(){
        ArrayList<Stock> allStocks = new ArrayList<>();
        allStocks.addAll(mDataSource.getStocks().values());
        //return sort(allStocks, mUserData.getSortPreference());
        return Tools.sort(allStocks,"A-Z");
    }

    public ArrayList<Stock> getWatchList(){
        ArrayList<Stock> watchlist = new ArrayList<>();
        for(Stock stock :mDataSource.getStocks().values()){
            if (stock.isInWatchList()){
                watchlist.add(stock);
            }
        }
        return Tools.sort(watchlist,mUserData.getSortPreference());
    }

    public void updateStocksData(String jsonData) throws JSONException{
        JSONParser jsonParser = new JSONParser(jsonData,getAllStocks());
        ArrayList<Stock> stocks = jsonParser.getStocks();
        for (Stock stock:stocks){
            mDataSource.updateStock(stock);
        }
    }

    public Wallet getWallet(){
        mWallet.setInvestmentList(getAllStocks());
        return mWallet;
    }

    public Market getMarket(){
        return new Market(mDataSource.getIndex(),mDataSource.getBrent());
    }

    //Get User Data
    public UserData getUserData() {
        return mUserData;
    }


    public ArrayList<Checklist> getChecklists() {
        //TODO construct a mechanism to save checklists and rules in the Database or shared preferences
        mChecklists.add(new Checklist("Buffet Checklist"));

        // Adding rules
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(Rule.getRule("PE Ratio", "<", "15.0"));
        rules.add(Rule.getRule("PBV Ratio", "<", "1.5"));
        mChecklists.get(0).setRules(rules);
        return mChecklists;
        //return mChecklists;
    }

    public HashMap<Stock,Checklist> getNotificationRules(){

        HashMap<Stock,Checklist> noti = new HashMap<>();
        Checklist mersChecklist = new Checklist("mers_checklist");
        //mersChecklist.addRule(Rule.getRule("Volume", "<", "80000"));
        Checklist bresCL = new Checklist("bres");
        bresCL.addRule(Rule.getRule("PE Ratio","<","15.0"));
        bresCL.addRule(Rule.getRule("Volume", ">", "0"));
        Checklist markCL = new Checklist("mark");
        markCL.addRule(Rule.getRule("Volume", ">", "10000000"));

        noti.put(Tools.getStockFromList("MERS", getAllStocks()), mersChecklist);
        noti.put(Tools.getStockFromList("BRES",getAllStocks()),bresCL);
        noti.put(Tools.getStockFromList("MARK",getAllStocks()),markCL);

        return noti;
    }

    public void setChecklists(ArrayList<Checklist> checklists) {
        mUserData.setChecklist(checklists);
    }

}
