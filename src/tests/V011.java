package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V011 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"teacher");
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
        Functions.click(tester,"Assignments",0);
        tester.assertMatch("Manage Assignments");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"assignments","Add");
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"assignments","Add");
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Vulnerabilities.selectclass(tester,"assignments","Add");
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
