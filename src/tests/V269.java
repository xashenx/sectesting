package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V269 {
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
		Vulnerabilities.page(tester,"classes","Add");
        tester.assertMatch("Add Class");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"classes","Add");
        tester.assertMatch("Add Class");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void fullyear(){
		Functions.click(tester,"Add",1);
		tester.assertMatch("Add Class");
		IElement fullyearButton = tester.getElementByXPath("//form[@name='addclass']//input[@value='Full Year']");
		fullyearButton.setAttribute("onClick","document.addclass.submit();");
        IElement fullyear = tester.getElementByXPath("//form[@name='addclass']//input[@name='fullyear']");
        fullyear.setAttribute("value","1'><a href=http://www.unitn.it>malicious</a>");
		Functions.click(tester,"Full Year",1);
        tester.assertMatch("Add Class");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
