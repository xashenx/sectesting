package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V071 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
		Functions.click(tester,"Semesters",0);
		tester.assertMatch("Manage Semesters");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"semesters","Add");
        tester.assertMatch("Add New Semester");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"semesters","Add");
        tester.assertMatch("Add New Semester");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
