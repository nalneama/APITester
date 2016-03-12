package test;

import com.nasserapps.saham.Model.Database.JSONParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nasser on 12/24/15.
 */
public class JSONParserTest {

    JSONParser mJSONParser;
    static final String JSONZeroStockExampleData ="{\n" +
            "\"query\": {\n" +
            "\"count\": 0,\n" +
            "\"created\": \"2016-01-17T06:54:24Z\",\n" +
            "\"lang\": \"en-US\",\n" +
            "\"results\": null\n" +
            "}\n" +
            "}";

    static final String JSONOneStockExampleData ="{\n" +
            "\"query\": {\n" +
            "\"count\": 1,\n" +
            "\"created\": \"2015-12-25T20:29:27Z\",\n" +
            "\"lang\": \"en-US\",\n" +
            "\"results\": {\n" +
            "\"quote\": {\n" +
            "\"symbol\": \"QIGD.QA\",\n" +
            "\"Ask\": \"37.45\",\n" +
            "\"AverageDailyVolume\": null,\n" +
            "\"Bid\": \"37.25\",\n" +
            "\"AskRealtime\": null,\n" +
            "\"BidRealtime\": null,\n" +
            "\"BookValue\": \"19.14\",\n" +
            "\"Change_PercentChange\": \"-0.45 - -1.19%\",\n" +
            "\"Change\": \"-0.45\",\n" +
            "\"Commission\": null,\n" +
            "\"Currency\": \"QAR\",\n" +
            "\"ChangeRealtime\": null,\n" +
            "\"AfterHoursChangeRealtime\": null,\n" +
            "\"DividendShare\": null,\n" +
            "\"LastTradeDate\": \"12/24/2015\",\n" +
            "\"TradeDate\": null,\n" +
            "\"EarningsShare\": \"1.93\",\n" +
            "\"ErrorIndicationreturnedforsymbolchangedinvalid\": null,\n" +
            "\"EPSEstimateCurrentYear\": null,\n" +
            "\"EPSEstimateNextYear\": null,\n" +
            "\"EPSEstimateNextQuarter\": \"0.00\",\n" +
            "\"DaysLow\": \"37.30\",\n" +
            "\"DaysHigh\": \"38.90\",\n" +
            "\"YearLow\": \"33.30\",\n" +
            "\"YearHigh\": \"56.50\",\n" +
            "\"HoldingsGainPercent\": null,\n" +
            "\"AnnualizedGain\": null,\n" +
            "\"HoldingsGain\": null,\n" +
            "\"HoldingsGainPercentRealtime\": null,\n" +
            "\"HoldingsGainRealtime\": null,\n" +
            "\"MoreInfo\": null,\n" +
            "\"OrderBookRealtime\": null,\n" +
            "\"MarketCapitalization\": \"4.62B\",\n" +
            "\"MarketCapRealtime\": null,\n" +
            "\"EBITDA\": \"304.33M\",\n" +
            "\"ChangeFromYearLow\": \"4.15\",\n" +
            "\"PercentChangeFromYearLow\": \"+12.46%\",\n" +
            "\"LastTradeRealtimeWithTime\": null,\n" +
            "\"ChangePercentRealtime\": null,\n" +
            "\"ChangeFromYearHigh\": \"-19.05\",\n" +
            "\"PercebtChangeFromYearHigh\": \"-33.72%\",\n" +
            "\"LastTradeWithTime\": \"1:12pm - <b>37.45</b>\",\n" +
            "\"LastTradePriceOnly\": \"37.45\",\n" +
            "\"HighLimit\": null,\n" +
            "\"LowLimit\": null,\n" +
            "\"DaysRange\": \"37.30 - 38.90\",\n" +
            "\"DaysRangeRealtime\": null,\n" +
            "\"FiftydayMovingAverage\": \"0.00\",\n" +
            "\"TwoHundreddayMovingAverage\": \"0.00\",\n" +
            "\"ChangeFromTwoHundreddayMovingAverage\": \"37.45\",\n" +
            "\"PercentChangeFromTwoHundreddayMovingAverage\": \"+inf%\",\n" +
            "\"ChangeFromFiftydayMovingAverage\": \"37.45\",\n" +
            "\"PercentChangeFromFiftydayMovingAverage\": \"+inf%\",\n" +
            "\"Name\": \"Qatari Investors Group QSC\",\n" +
            "\"Notes\": null,\n" +
            "\"Open\": \"37.90\",\n" +
            "\"PreviousClose\": \"37.90\",\n" +
            "\"PricePaid\": null,\n" +
            "\"ChangeinPercent\": \"-1.19%\",\n" +
            "\"PriceSales\": \"7.13\",\n" +
            "\"PriceBook\": \"1.98\",\n" +
            "\"ExDividendDate\": null,\n" +
            "\"PERatio\": \"19.43\",\n" +
            "\"DividendPayDate\": null,\n" +
            "\"PERatioRealtime\": null,\n" +
            "\"PEGRatio\": \"0.00\",\n" +
            "\"PriceEPSEstimateCurrentYear\": null,\n" +
            "\"PriceEPSEstimateNextYear\": null,\n" +
            "\"Symbol\": \"QIGD.QA\",\n" +
            "\"SharesOwned\": null,\n" +
            "\"ShortRatio\": \"0.00\",\n" +
            "\"LastTradeTime\": \"1:12pm\",\n" +
            "\"TickerTrend\": null,\n" +
            "\"OneyrTargetPrice\": null,\n" +
            "\"Volume\": \"136289\",\n" +
            "\"HoldingsValue\": null,\n" +
            "\"HoldingsValueRealtime\": null,\n" +
            "\"YearRange\": \"33.30 - 56.50\",\n" +
            "\"DaysValueChange\": null,\n" +
            "\"DaysValueChangeRealtime\": null,\n" +
            "\"StockExchange\": \"DOH\",\n" +
            "\"DividendYield\": null,\n" +
            "\"PercentChange\": \"-1.19%\"\n" +
            "}\n" +
            "}\n" +
            "}\n" +
            "}\n";

    String JSONTwoStocksExampleData = "{\n" +
        "\"query\": {\n" +
        "\"count\": 2,\n" +
        "\"created\": \"2016-01-16T16:15:24Z\",\n" +
        "\"lang\": \"en-US\",\n" +
        "\"results\": {\n" +
        "\"quote\": [\n" +
        "{\n" +
        "\"symbol\": \"QIGD.QA\",\n" +
        "\"Ask\": \"30.40\",\n" +
        "\"AverageDailyVolume\": null,\n" +
        "\"Bid\": \"30.00\",\n" +
        "\"AskRealtime\": null,\n" +
        "\"BidRealtime\": null,\n" +
        "\"BookValue\": \"19.14\",\n" +
        "\"Change_PercentChange\": \"-1.50 - -4.70%\",\n" +
        "\"Change\": \"-1.50\",\n" +
        "\"Commission\": null,\n" +
        "\"Currency\": \"QAR\",\n" +
        "\"ChangeRealtime\": null,\n" +
        "\"AfterHoursChangeRealtime\": null,\n" +
        "\"DividendShare\": null,\n" +
        "\"LastTradeDate\": \"1/14/2016\",\n" +
        "\"TradeDate\": null,\n" +
        "\"EarningsShare\": \"1.93\",\n" +
        "\"ErrorIndicationreturnedforsymbolchangedinvalid\": null,\n" +
        "\"EPSEstimateCurrentYear\": null,\n" +
        "\"EPSEstimateNextYear\": null,\n" +
        "\"EPSEstimateNextQuarter\": \"0.00\",\n" +
        "\"DaysLow\": \"29.15\",\n" +
        "\"DaysHigh\": \"31.10\",\n" +
        "\"YearLow\": \"29.15\",\n" +
        "\"YearHigh\": \"56.50\",\n" +
        "\"HoldingsGainPercent\": null,\n" +
        "\"AnnualizedGain\": null,\n" +
        "\"HoldingsGain\": null,\n" +
        "\"HoldingsGainPercentRealtime\": null,\n" +
        "\"HoldingsGainRealtime\": null,\n" +
        "\"MoreInfo\": null,\n" +
        "\"OrderBookRealtime\": null,\n" +
        "\"MarketCapitalization\": \"3.75B\",\n" +
        "\"MarketCapRealtime\": null,\n" +
        "\"EBITDA\": \"304.33M\",\n" +
        "\"ChangeFromYearLow\": \"1.25\",\n" +
        "\"PercentChangeFromYearLow\": \"+4.29%\",\n" +
        "\"LastTradeRealtimeWithTime\": null,\n" +
        "\"ChangePercentRealtime\": null,\n" +
        "\"ChangeFromYearHigh\": \"-26.10\",\n" +
        "\"PercebtChangeFromYearHigh\": \"-46.19%\",\n" +
        "\"LastTradeWithTime\": \"1:13pm - <b>30.40</b>\",\n" +
        "\"LastTradePriceOnly\": \"30.40\",\n" +
        "\"HighLimit\": null,\n" +
        "\"LowLimit\": null,\n" +
        "\"DaysRange\": \"29.15 - 31.10\",\n" +
        "\"DaysRangeRealtime\": null,\n" +
        "\"FiftydayMovingAverage\": \"0.00\",\n" +
        "\"TwoHundreddayMovingAverage\": \"0.00\",\n" +
        "\"ChangeFromTwoHundreddayMovingAverage\": \"30.40\",\n" +
        "\"PercentChangeFromTwoHundreddayMovingAverage\": \"+inf%\",\n" +
        "\"ChangeFromFiftydayMovingAverage\": \"30.40\",\n" +
        "\"PercentChangeFromFiftydayMovingAverage\": \"+inf%\",\n" +
        "\"Name\": \"Qatari Investors Group QSC\",\n" +
        "\"Notes\": null,\n" +
        "\"Open\": \"31.00\",\n" +
        "\"PreviousClose\": \"31.90\",\n" +
        "\"PricePaid\": null,\n" +
        "\"ChangeinPercent\": \"-4.70%\",\n" +
        "\"PriceSales\": \"6.00\",\n" +
        "\"PriceBook\": \"1.67\",\n" +
        "\"ExDividendDate\": null,\n" +
        "\"PERatio\": \"15.78\",\n" +
        "\"DividendPayDate\": null,\n" +
        "\"PERatioRealtime\": null,\n" +
        "\"PEGRatio\": \"0.00\",\n" +
        "\"PriceEPSEstimateCurrentYear\": null,\n" +
        "\"PriceEPSEstimateNextYear\": null,\n" +
        "\"Symbol\": \"QIGD.QA\",\n" +
        "\"SharesOwned\": null,\n" +
        "\"ShortRatio\": \"0.00\",\n" +
        "\"LastTradeTime\": \"1:13pm\",\n" +
        "\"TickerTrend\": null,\n" +
        "\"OneyrTargetPrice\": null,\n" +
        "\"Volume\": \"63213\",\n" +
        "\"HoldingsValue\": null,\n" +
        "\"HoldingsValueRealtime\": null,\n" +
        "\"YearRange\": \"29.15 - 56.50\",\n" +
        "\"DaysValueChange\": null,\n" +
        "\"DaysValueChangeRealtime\": null,\n" +
        "\"StockExchange\": \"DOH\",\n" +
        "\"DividendYield\": null,\n" +
        "\"PercentChange\": \"-4.70%\"\n" +
        "},\n" +
        "{\n" +
        "\"symbol\": \"MRDS.QA\",\n" +
        "\"Ask\": \"12.10\",\n" +
        "\"AverageDailyVolume\": null,\n" +
        "\"Bid\": \"11.99\",\n" +
        "\"AskRealtime\": null,\n" +
        "\"BidRealtime\": null,\n" +
        "\"BookValue\": \"12.66\",\n" +
        "\"Change_PercentChange\": \"-0.81 - -6.33%\",\n" +
        "\"Change\": \"-0.81\",\n" +
        "\"Commission\": null,\n" +
        "\"Currency\": \"QAR\",\n" +
        "\"ChangeRealtime\": null,\n" +
        "\"AfterHoursChangeRealtime\": null,\n" +
        "\"DividendShare\": null,\n" +
        "\"LastTradeDate\": \"1/14/2016\",\n" +
        "\"TradeDate\": null,\n" +
        "\"EarningsShare\": \"1.66\",\n" +
        "\"ErrorIndicationreturnedforsymbolchangedinvalid\": null,\n" +
        "\"EPSEstimateCurrentYear\": null,\n" +
        "\"EPSEstimateNextYear\": null,\n" +
        "\"EPSEstimateNextQuarter\": \"0.00\",\n" +
        "\"DaysLow\": \"11.84\",\n" +
        "\"DaysHigh\": \"12.54\",\n" +
        "\"YearLow\": \"11.84\",\n" +
        "\"YearHigh\": \"22.17\",\n" +
        "\"HoldingsGainPercent\": null,\n" +
        "\"AnnualizedGain\": null,\n" +
        "\"HoldingsGain\": null,\n" +
        "\"HoldingsGainPercentRealtime\": null,\n" +
        "\"HoldingsGainRealtime\": null,\n" +
        "\"MoreInfo\": null,\n" +
        "\"OrderBookRealtime\": null,\n" +
        "\"MarketCapitalization\": \"1.26B\",\n" +
        "\"MarketCapRealtime\": null,\n" +
        "\"EBITDA\": \"247.23M\",\n" +
        "\"ChangeFromYearLow\": \"0.15\",\n" +
        "\"PercentChangeFromYearLow\": \"+1.27%\",\n" +
        "\"LastTradeRealtimeWithTime\": null,\n" +
        "\"ChangePercentRealtime\": null,\n" +
        "\"ChangeFromYearHigh\": \"-10.18\",\n" +
        "\"PercebtChangeFromYearHigh\": \"-45.92%\",\n" +
        "\"LastTradeWithTime\": \"1:10pm - <b>11.99</b>\",\n" +
        "\"LastTradePriceOnly\": \"11.99\",\n" +
        "\"HighLimit\": null,\n" +
        "\"LowLimit\": null,\n" +
        "\"DaysRange\": \"11.84 - 12.54\",\n" +
        "\"DaysRangeRealtime\": null,\n" +
        "\"FiftydayMovingAverage\": \"0.00\",\n" +
        "\"TwoHundreddayMovingAverage\": \"0.00\",\n" +
        "\"ChangeFromTwoHundreddayMovingAverage\": \"11.99\",\n" +
        "\"PercentChangeFromTwoHundreddayMovingAverage\": \"+inf%\",\n" +
        "\"ChangeFromFiftydayMovingAverage\": \"11.99\",\n" +
        "\"PercentChangeFromFiftydayMovingAverage\": \"+inf%\",\n" +
        "\"Name\": \"Mazaya Qatar Real Estate Develo\",\n" +
        "\"Notes\": null,\n" +
        "\"Open\": \"12.54\",\n" +
        "\"PreviousClose\": \"12.80\",\n" +
        "\"PricePaid\": null,\n" +
        "\"ChangeinPercent\": \"-6.33%\",\n" +
        "\"PriceSales\": \"3.57\",\n" +
        "\"PriceBook\": \"1.01\",\n" +
        "\"ExDividendDate\": null,\n" +
        "\"PERatio\": \"7.24\",\n" +
        "\"DividendPayDate\": null,\n" +
        "\"PERatioRealtime\": null,\n" +
        "\"PEGRatio\": \"0.00\",\n" +
        "\"PriceEPSEstimateCurrentYear\": null,\n" +
        "\"PriceEPSEstimateNextYear\": null,\n" +
        "\"Symbol\": \"MRDS.QA\",\n" +
        "\"SharesOwned\": null,\n" +
        "\"ShortRatio\": \"0.00\",\n" +
        "\"LastTradeTime\": \"1:10pm\",\n" +
        "\"TickerTrend\": null,\n" +
        "\"OneyrTargetPrice\": null,\n" +
        "\"Volume\": \"556321\",\n" +
        "\"HoldingsValue\": null,\n" +
        "\"HoldingsValueRealtime\": null,\n" +
        "\"YearRange\": \"11.84 - 22.17\",\n" +
        "\"DaysValueChange\": null,\n" +
        "\"DaysValueChangeRealtime\": null,\n" +
        "\"StockExchange\": \"DOH\",\n" +
        "\"DividendYield\": null,\n" +
        "\"PercentChange\": \"-6.33%\"\n" +
        "}\n" +
        "]\n" +
        "}\n" +
        "}\n" +
        "}\n";

    private final String JSONIndexExampleData = "{\n" +
            "\"query\": {\n" +
            "\"count\": 1,\n" +
            "\"created\": \"2016-01-19T11:52:50Z\",\n" +
            "\"lang\": \"en-us\",\n" +
            "\"results\": {\n" +
            "\"quote\": {\n" +
            "\"symbol\": \"BZH16.NYM\",\n" +
            "\"Ask\": \"29.96\",\n" +
            "\"AverageDailyVolume\": null,\n" +
            "\"Bid\": \"29.93\",\n" +
            "\"AskRealtime\": null,\n" +
            "\"BidRealtime\": null,\n" +
            "\"BookValue\": \"0.00\",\n" +
            "\"Change_PercentChange\": \"+1.01 - +3.49%\",\n" +
            "\"Change\": \"+1.01\",\n" +
            "\"Commission\": null,\n" +
            "\"Currency\": \"USD\",\n" +
            "\"ChangeRealtime\": null,\n" +
            "\"AfterHoursChangeRealtime\": null,\n" +
            "\"DividendShare\": null,\n" +
            "\"LastTradeDate\": \"1/19/2016\",\n" +
            "\"TradeDate\": null,\n" +
            "\"EarningsShare\": null,\n" +
            "\"ErrorIndicationreturnedforsymbolchangedinvalid\": null,\n" +
            "\"EPSEstimateCurrentYear\": null,\n" +
            "\"EPSEstimateNextYear\": null,\n" +
            "\"EPSEstimateNextQuarter\": \"0.00\",\n" +
            "\"DaysLow\": \"27.67\",\n" +
            "\"DaysHigh\": \"29.97\",\n" +
            "\"YearLow\": \"27.67\",\n" +
            "\"YearHigh\": \"29.97\",\n" +
            "\"HoldingsGainPercent\": null,\n" +
            "\"AnnualizedGain\": null,\n" +
            "\"HoldingsGain\": null,\n" +
            "\"HoldingsGainPercentRealtime\": null,\n" +
            "\"HoldingsGainRealtime\": null,\n" +
            "\"MoreInfo\": null,\n" +
            "\"OrderBookRealtime\": null,\n" +
            "\"MarketCapitalization\": null,\n" +
            "\"MarketCapRealtime\": null,\n" +
            "\"EBITDA\": null,\n" +
            "\"ChangeFromYearLow\": \"2.28\",\n" +
            "\"PercentChangeFromYearLow\": \"+8.24%\",\n" +
            "\"LastTradeRealtimeWithTime\": null,\n" +
            "\"ChangePercentRealtime\": null,\n" +
            "\"ChangeFromYearHigh\": \"-0.02\",\n" +
            "\"PercebtChangeFromYearHigh\": \"-0.07%\",\n" +
            "\"LastTradeWithTime\": \"6:22am - <b>29.95</b>\",\n" +
            "\"LastTradePriceOnly\": \"29.95\",\n" +
            "\"HighLimit\": null,\n" +
            "\"LowLimit\": null,\n" +
            "\"DaysRange\": \"27.67 - 29.97\",\n" +
            "\"DaysRangeRealtime\": null,\n" +
            "\"FiftydayMovingAverage\": null,\n" +
            "\"TwoHundreddayMovingAverage\": null,\n" +
            "\"ChangeFromTwoHundreddayMovingAverage\": null,\n" +
            "\"PercentChangeFromTwoHundreddayMovingAverage\": null,\n" +
            "\"ChangeFromFiftydayMovingAverage\": null,\n" +
            "\"PercentChangeFromFiftydayMovingAverage\": null,\n" +
            "\"Name\": \"Brent Crude Oil Last Day Financ\",\n" +
            "\"Notes\": null,\n" +
            "\"Open\": \"28.80\",\n" +
            "\"PreviousClose\": \"28.94\",\n" +
            "\"PricePaid\": null,\n" +
            "\"ChangeinPercent\": \"+3.49%\",\n" +
            "\"PriceSales\": null,\n" +
            "\"PriceBook\": null,\n" +
            "\"ExDividendDate\": null,\n" +
            "\"PERatio\": null,\n" +
            "\"DividendPayDate\": null,\n" +
            "\"PERatioRealtime\": null,\n" +
            "\"PEGRatio\": \"0.00\",\n" +
            "\"PriceEPSEstimateCurrentYear\": null,\n" +
            "\"PriceEPSEstimateNextYear\": null,\n" +
            "\"Symbol\": \"BZH16.NYM\",\n" +
            "\"SharesOwned\": null,\n" +
            "\"ShortRatio\": null,\n" +
            "\"LastTradeTime\": \"6:22am\",\n" +
            "\"TickerTrend\": null,\n" +
            "\"OneyrTargetPrice\": null,\n" +
            "\"Volume\": \"20266\",\n" +
            "\"HoldingsValue\": null,\n" +
            "\"HoldingsValueRealtime\": null,\n" +
            "\"YearRange\": \"27.67 - 29.97\",\n" +
            "\"DaysValueChange\": null,\n" +
            "\"DaysValueChangeRealtime\": null,\n" +
            "\"StockExchange\": \"NYM\",\n" +
            "\"DividendYield\": null,\n" +
            "\"PercentChange\": \"+3.49%\"\n" +
            "}\n" +
            "}\n" +
            "}\n" +
            "}\n";

    @Test( expected = IndexOutOfBoundsException.class)
    public void testTieDataForZeroStock() throws Exception {
        mJSONParser = new JSONParser(JSONZeroStockExampleData,null);
        Assert.assertEquals("QIGD.QA", mJSONParser.getStocks().get(0).getSymbol());
    }
    @Test
        public void testTieDataForOneStock() throws Exception {
        mJSONParser = new JSONParser(JSONOneStockExampleData,null);
        Assert.assertEquals("QIGD", mJSONParser.getStocks().get(0).getSymbol());
    }

    @Test
    public void testTieDataForTwoStocks() throws Exception {
        mJSONParser = new JSONParser(JSONTwoStocksExampleData,null);
        Assert.assertEquals("MRDS", mJSONParser.getStocks().get(1).getSymbol());
        }

    @Test
    public void testTieDataForIndex() throws Exception {
        mJSONParser = new JSONParser(JSONIndexExampleData,null);
        mJSONParser.getStocks();
        Assert.assertEquals("BZH", mJSONParser.getIndexes().get(0).getSymbol());
    }

}