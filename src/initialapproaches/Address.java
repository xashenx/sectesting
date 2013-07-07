package initialapproaches;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;

public class Address {
    private static WebTester tester;
    String oldValue;
    
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
	}
	
	@Test
	public void address(){
        Functions.login(tester,"admin");
        Functions.click(tester,"School",0);
        tester.assertMatch("Manage School Information");
        oldValue = tester.getElementByXPath("//input [@name='schooladdress']").getAttribute("value");
        tester.setTextField("schooladdress", oldValue + "\'><a href>a</a>");
        Functions.click(tester," Update ",1);
        tester.assertLinkNotPresentWithText("a");
	}
	@After
	public void cleanup(){
        tester.setTextField("schooladdress", oldValue);
        Functions.click(tester," Update ",1);
        tester.setTextField("schooladdress", oldValue);
        Functions.click(tester," Update ",1);
        Functions.click(tester,"Log Out",0);
        tester = null;
	}
}
