package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V087 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"parent");
        tester.assertMatch("Students of Donald Duck");
        Functions.click(tester,"Huey Duck",0);
        tester.assertMatch("Huey Duck's Classes");
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
	}
	
	@Test
	public void page() {
		Vulnerabilities.page(tester, "student", null);
		Functions.click(tester, "Settings", 0);
		tester.assertMatch("Class Settings");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester, "student", "Settings",
				"document.student.submit();","parent");
        tester.assertMatch("Class Settings");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Vulnerabilities.selectclass(tester,"student",null);
		Functions.click(tester, "Settings", 0);
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
