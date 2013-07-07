package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V212 {
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
		tester.selectOption("report","Points Report");
		tester.assertMatch("Points Report");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		IElement mySelect = tester.getElementByXPath("//option[text()='Points Report']");
		String optionValue = mySelect.getAttribute("value");
		mySelect.setAttribute("value",optionValue + "'><a href='http://www.unitn.it'>malicious</a><br'");
		tester.selectOption("report","Points Report");
		tester.assertMatch("Points Report");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
