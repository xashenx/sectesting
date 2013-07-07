package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V230 {
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
		Vulnerabilities.page(tester,"classes","Show in Grid");
        tester.assertMatch("School Class Schedule");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"classes","Show in Grid");
        tester.assertMatch("School Class Schedule");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
