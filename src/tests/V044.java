package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V044 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"admin");
		Functions.click(tester,"Terms",0);
		tester.assertMatch("Manage Terms");
		IElement myCheckbox = tester.getElementByXPath("//td[text()='2012-2013']/..//input[@type='checkbox']");
		tester.setWorkingForm("terms");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"terms","Edit");
		tester.assertMatch("Edit Term");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"terms","Edit");
		tester.assertMatch("Edit Term");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void delete(){
		Vulnerabilities.delete(tester,"terms","Edit","2012-2013");
		tester.assertMatch("EditTerm.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
