package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V161 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"admin");
		tester.assertMatch("Manage Classes");
		Functions.click(tester,"Parents",0);
		IElement myCheckbox = tester.getElementByXPath("//td[text()='Donald']/..//input[@type='checkbox']");
		tester.setWorkingForm("parents");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"parents","Edit");
		tester.assertMatch("Edit Parent");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"parents","Edit");
		tester.assertMatch("Edit Parent");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void delete(){
		Vulnerabilities.delete(tester,"parents","Edit","Donald");
		tester.assertMatch("EditParent.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
