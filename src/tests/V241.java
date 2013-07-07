package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V241 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"admin");
		Functions.click(tester,"Students",0);
		tester.assertMatch("Manage Students");
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"students",null);
		tester.selectOption("report","Grade Report");
		tester.assertMatch("Grade Report");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		IElement mySelect = tester.getElementByXPath("//option[text()='Grade Report']");
		String optionValue = mySelect.getAttribute("value");
		mySelect.setAttribute("value",optionValue + "'><a href='http://www.unitn.it'>malicious</a><br'");
		tester.selectOption("report","Grade Report");
		tester.assertMatch("Grade Report");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
