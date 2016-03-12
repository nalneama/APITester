package com.nasserapps.saham.Controllers.InProgress;

public class Calculator {

    private static final double BROOKER_PERCENTAGE = 0.00275;
    private static final double MINIMUM_FEES= 30;

    public static double getAveragePrice(double currentAveragePrice, double currentMarketPrice, int currentQuantity, int addedQuantity){
        double NewAveragePrice;

        NewAveragePrice= (currentAveragePrice * currentQuantity+currentMarketPrice*addedQuantity)/(currentQuantity+addedQuantity);

        return NewAveragePrice;
    }

    //returns the Quantity of stocks required to get the desired Average Price
    public static int getRequiredQuantity(double currentAveragePrice, double currentMarketPrice, int currentQuantity, double desiredAveragePrice){
        int requiredQuantity;

        requiredQuantity = (int)((desiredAveragePrice - currentAveragePrice)/(currentMarketPrice-desiredAveragePrice)*currentQuantity);

        return requiredQuantity;
    }

    //returns the required Stock Price to get the desired Average Price
    public static double getRequiredPrice(double currentAveragePrice, int currentQuantity, double desiredAveragePrice, int addedQuantity){
        double requiredPrice;

        requiredPrice = desiredAveragePrice+desiredAveragePrice*currentQuantity/addedQuantity-currentAveragePrice*currentQuantity/addedQuantity;

        return requiredPrice;
    }

    public static double getBrookerFee(double totalPrice) {

        if (totalPrice> 10909.09){
            return totalPrice*BROOKER_PERCENTAGE;
        }
        else{
            return MINIMUM_FEES;
        }
    }
}
