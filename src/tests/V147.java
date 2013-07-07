package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V147 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"student");
		tester.assertMatch("Harry Potter's Classes");
		Functions.click(tester,"Music",0);
		tester.assertMatch("Class Settings");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"student",null);
		Functions.click(tester,"Announcements",0);
        tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"student","Announcements","document.student.submit();","student");
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
