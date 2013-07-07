package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.MySql;
import util.Vulnerabilities;

public class V105 {
	private static WebTester tester;
	private static String oldValue;

	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester, "admin");
		Functions.click(tester, "School", 0);
		tester.assertMatch("Manage School Information");
		IElement textArea = tester
				.getElementByXPath("//textarea [@name='sitemessage']");
		oldValue = textArea.getTextContent();
		tester.setTextField("sitemessage", "<a href>malicious</a>");
		Functions.click(tester, " Update ", 1);
		Functions.click(tester, "Log Out", 0);
		tester.assertMatch("Today's Message");
	}

	@Test
	public void page() {
		Vulnerabilities.page(tester, "login","Login");
		tester.assertMatch("Today's Message");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void message() {
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void messageSQL(){
		Functions.login(tester,"student");
		MySql.executeUpdate("UPDATE schoolinfo SET" +
					"`sitemessage`= '<a href>malicious</a>'");
		Functions.click(tester, "Log Out", 0);
		tester.assertLinkNotPresentWithExactText("malicious");
	}

	@After
	public void cleanup() {
		Functions.login(tester, "admin");
		Functions.click(tester, "School", 0);
		tester.assertMatch("Manage School Information");
		tester.setTextField("sitemessage", oldValue);
		Functions.click(tester, " Update ", 1);
		Functions.click(tester, "Log Out", 0);
		tester.assertLinkNotPresentWithText("malicious");
		tester = null;
	}
}
