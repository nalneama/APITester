package com.nasserapps.apitester;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nasser on 12/24/15.
 */
public class JSONParserTest {

    JSONParser mJSONParser;
    static final String JSONExampleData ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%3D%22QIGD.QA%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Before
    public void setUp() throws Exception {

    mJSONParser = new JSONParser(JSONExampleData);
    }

    @Test
    public void testTieData() throws Exception {
        String symbol = mJSONParser.getQuote().getString("Symbol");
        //assertEquals("QIGD",symbol);
    }
}