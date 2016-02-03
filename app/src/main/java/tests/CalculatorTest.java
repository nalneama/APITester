package tests;

import com.nasserapps.apitester.Model.Calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testGetAveragePrice() throws Exception {
        Assert.assertEquals(Calculator.getAveragePrice(50.1, 40.1, 1000, 1000), 45.1, 0.001);
    }

    @Test
    public void testGetRequiredQuantity() throws Exception {
        Assert.assertEquals(Calculator.getRequiredQuantity(50.1, 40.1, 1000, 45.1),1000);
    }

    @Test
    public void testGetRequiredPrice() throws Exception {

        Assert.assertEquals(Calculator.getRequiredPrice(50.1,1000,45.1,1000),40.1,0.001);
    }

    @Test
    public void testBrookerFee() throws Exception {
        Assert.assertEquals(Calculator.getBrookerFee(45000),123.75,0.001);
        Assert.assertEquals(Calculator.getBrookerFee(1),30,0.001);
    }
}