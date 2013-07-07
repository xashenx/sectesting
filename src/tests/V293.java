package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V293 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
		tester.assertMatch("Manage Classes");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"admin",null);
		Functions.click(tester,"Students",0);
        tester.assertMatch("Manage Students");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"admin","Students","document.admin.submit();","admin");
        tester.assertMatch("Manage Students");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void onpage(){
		Functions.click(tester,"Students",0);
		Vulnerabilities.onpage(tester,"1","students");
		Functions.click(tester,"1",0);
		tester.assertMatch("Manage Students");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
