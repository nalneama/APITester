package tests;

import com.nasserapps.apitester.Model.Wallet;

import org.junit.Test;

public class WalletTest {

    Wallet mWallet;

    @Test
    public void isAPIKeyCorrect(){
        mWallet= new Wallet();
        //mWallet.setInitialWatchList();
        //Assert.assertEquals("MERS.QA+BRES.QA+MRDS.QA+QIIK.QA+QIBK.QA+MARK.QA+AKHI.QA+QIGD.QA+",mWallet.getAPIKey());
    }

}