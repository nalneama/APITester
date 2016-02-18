package tests;

import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Checklists.ExpressionParser;
import com.nasserapps.apitester.Model.Checklists.Rule;
import com.nasserapps.apitester.Model.Ticker;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ExpressionParserTest {

    @Test
    public void testGetRule() throws Exception {
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(new ExpressionParser().getRule("PE Ratio", "<", "15.0"));
        rules.add(new ExpressionParser().getRule("PE Ratio", ">","10.0"));
        rules.add(new ExpressionParser().getRule( "Volume", "=", "500000"));
        Ticker stock= new Ticker();
        stock.setPERatio(22);
        stock.setVolume(500000);
        Assert.assertEquals(true, rules.get(0).evaluate(stock));
        Assert.assertEquals(false, rules.get(1).evaluate(stock));
        Assert.assertEquals(true, rules.get(2).evaluate(stock));

        Checklist c1 = new Checklist(rules);
        Assert.assertEquals(false, c1.isPassing(stock));
    }
}