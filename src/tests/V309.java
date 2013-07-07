package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V309 {
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
		Functions.click(tester,"Assignments",0);
		Vulnerabilities.page(tester,"assignments",null);
		Functions.click(tester,"1",0);
        tester.assertMatch("Manage Assignments");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"teacher","Assignments","document.teacher.submit();","teacher");
        tester.assertMatch("Manage Assignments");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Functions.click(tester,"Assignments",0);
		Vulnerabilities.selectclass(tester,"assignments",null);
		Functions.click(tester,"1",0);
        tester.assertMatch("ManageAssignments.php: Unable to get");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void onpage(){
		Functions.click(tester,"Assignments",0);
		Vulnerabilities.onpage(tester,"1","assignments");
		Functions.click(tester,"1",0);
		tester.assertMatch("Manage Assignments");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
