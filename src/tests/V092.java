package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.MySql;
import util.Vulnerabilities;

public class V092 {
	private static WebTester tester;
	private static String oldValue;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
		tester.assertMatch("Manage Classes");
		Functions.click(tester, "School", 0);
        oldValue = tester.getElementByXPath("//input [@name='schooladdress']").getAttribute("value");
        Functions.click(tester, "Classes", 0);
        tester.assertMatch("Manage Classes");
	}
	
	@Test
	public void page() {
		Vulnerabilities.page(tester, "admin", null);
		Functions.click(tester, "School", 0);
		tester.assertMatch("Manage School Information");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester, "admin", "School",
				"document.admin.submit();","admin");
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
//	@Test
//	public void phone(){
//		Vulnerabilities.page2Link(tester, "student", "Students",
//				"document.student.submit();");
//        tester.assertMatch("Students of Donald Duck");
//        tester.assertLinkNotPresentWithText("malicious");
//	}
//	
	@Test
	public void address(){
//        Functions.login(tester,"admin");
        Functions.click(tester,"School",0);
        tester.assertMatch("Manage School Information");
        IElement schooladdress = tester.getElementByXPath("//input[@name='schooladdress']");
        schooladdress.setAttribute("value",oldValue + "\\'><a href>malicious</a><br\\'");
        Functions.click(tester," Update ",1);
        tester.assertLinkNotPresentWithExactText("malicious");
	}
	
	@Test
	public void addressSQL(){
		MySql.executeUpdate("UPDATE schoolinfo SET" +
					"`address`= '" + oldValue + "\\'><a href>malicious</a><br\\''");
		Functions.click(tester,"School",0);
		tester.assertLinkNotPresentWithExactText("malicious");
	}
	
	@After
	public void cleanup(){
        Functions.click(tester," Update ",1);
        IElement schooladdress = tester.getElementByXPath("//input[@name='schooladdress']");
		schooladdress.setAttribute("value", oldValue);
        Functions.click(tester," Update ",1);
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
