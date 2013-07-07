package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V148 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"teacher");
		Functions.click(tester,"Music",0);
		tester.assertMatch("Class Settings");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"teacher",null);
		Functions.click(tester,"Announcements",0);
        tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"teacher","Announcements","document.teacher.submit();","teacher");
        tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void onpage(){
		Functions.click(tester,"Announcements",0);
		Vulnerabilities.onpage(tester,"1","announcements");
		Functions.click(tester,"1",0);
		tester.assertMatch("View Announcements");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
