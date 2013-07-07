package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V138 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"student");
		Functions.click(tester,"Music",0);
		tester.assertMatch("Class Settings");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"student",null);
	    Functions.click(tester,"Classes",0);
        tester.assertMatch("Harry Potter's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"student","Classes","document.student.submit();","student");
	    tester.assertMatch("Harry Potter's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
