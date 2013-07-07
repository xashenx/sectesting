package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V013 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
		Functions.click(tester,"Attendance",0);
		tester.assertMatch("Tardy");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"registration","Add");
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"registration","Add");
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void student(){
		Vulnerabilities.selectInputVulnerability(tester,"registration","Add","student");
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void semester(){
		Vulnerabilities.selectInputVulnerability(tester,"registration","Add","semester");
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
