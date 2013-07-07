package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V093 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
		tester.assertMatch("Manage Classes");
		Functions.click(tester, "Parents", 0);
		tester.assertMatch("Manage Parents");
	}
	
	@Test
	public void page() {
		Vulnerabilities.page(tester, "parents", "Add");
		tester.assertMatch("Add New Parent");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester, "parents", "Add");
        tester.assertMatch("Add New Parent");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
