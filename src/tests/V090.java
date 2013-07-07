package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V090 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"parent");
		tester.assertMatch("Students of Donald Duck");
	}
	
	@Test
	public void page() {
		Vulnerabilities.page(tester, "student", null);
		Functions.click(tester, "Students", 0);
		tester.assertMatch("Students of Donald Duck");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester, "student", "Students",
				"document.student.submit();","parent");
        tester.assertMatch("Students of Donald Duck");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
