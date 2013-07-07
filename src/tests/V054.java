package tests;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.*;

import util.Functions;
import util.MySql;

public class V054 {
    private static WebTester tester;
    String oldValue;
    
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
    	Functions.login(tester,"admin");
        Functions.click(tester,"School",0);
        tester.assertMatch("Manage School Information");
        oldValue = tester.getElementByXPath("//textarea [@name='sitetext']").getTextContent();
	}
	
	@Test
	public void siteText(){
        tester.setTextField("sitetext", "<a href=\"http://www.unitn.it\">malicious</a>");
        Functions.click(tester," Update ",1);
        Functions.click(tester,"Log Out",0);
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void siteTextSQL(){
		MySql.executeUpdate("UPDATE schoolinfo SET sitetext = '<a href>malicious</a>'");
		Functions.click(tester,"Log Out",0);
		tester.assertLinkNotPresentWithExactText("malicious");
	}
	
	@After
	public void cleanup(){
        tester.assertMatch("Today's Message");
        Functions.login(tester, "admin");
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        tester.setTextField("sitetext", oldValue);
        Functions.click(tester," Update ",1);
        Functions.click(tester,"Log Out",0);
        tester = null;
	}
}
