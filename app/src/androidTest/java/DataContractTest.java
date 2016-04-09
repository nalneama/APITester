import android.net.Uri;
import android.test.AndroidTestCase;

import com.nasserapps.saham.Model.Database.DataContract;

public class DataContractTest extends AndroidTestCase {


        // intentionally includes a slash to make sure Uri is getting quoted correctly
        private static final String TEST_STOCK = "/MERS";
        //private static final long TEST_WEATHER_DATE = 1419033600L;  // December 20th, 2014

    /*
        Students: Uncomment this out to test your weather location function.
     */
    public void testBuildStockContract() {
        Uri uri = DataContract.StocksEntry.buildStockFromSymbol(TEST_STOCK);
        assertNotNull("Error: Null Uri returned.  You must fill-in buildWeatherLocation in " +
                        "WeatherContract.",
                uri);
//        assertEquals("Error: Weather location not properly appended to the end of the Uri",
//                TEST_STOCK, uri.getLastPathSegment());
//        assertEquals("Error: Weather location Uri doesn't match our expected result",
//                uri.toString(),
//                "content://com.nasserapps.apitester.app/stock/MERS");
    }
    }