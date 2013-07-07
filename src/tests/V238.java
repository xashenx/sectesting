package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V238 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
        Functions.click(tester,"Registration",0);
        tester.assertMatch("Period Number");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"registration","Show in Grid");
        tester.assertMatch("Dewey Duck's Schedule");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"registration","Show in Grid");
        tester.assertMatch("Dewey Duck's Schedule");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
