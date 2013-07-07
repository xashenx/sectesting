package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V142 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"parent");
		Functions.click(tester,"Huey Duck",0);
		tester.assertMatch("Huey Duck's Classes");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"student",null);
		Functions.click(tester,"Classes",0);
        tester.assertMatch("Huey Duck's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"student","Classes","document.student.submit();","parent");
		tester.assertMatch("Huey Duck's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void student(){
        IElement student = tester.getElementByXPath("//form[@name='student']//input[@name='student']");
        String oldValue = student.getAttribute("value");
        student.setAttribute("value",oldValue +"';<a href=http://www.unitn.it>malicious</a>");
		Functions.click(tester,"Classes",0);
        tester.assertMatch("ParentViewCourses.php: Unable to get the studentid 2");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
