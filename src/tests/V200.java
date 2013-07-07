package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V200 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"parent");
        tester.assertMatch("Students of Donald Duck");
        Functions.click(tester,"Huey Duck",0);
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"student",null);
		Functions.click(tester,"Grades",0);
        tester.assertMatch("View Grades");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"student","Grades","document.student.submit();","parent");
        tester.assertMatch("View Grades");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Vulnerabilities.selectclass(tester,"student",null);
		Functions.click(tester,"Grades",0);
        tester.assertMatch("View Grades");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
