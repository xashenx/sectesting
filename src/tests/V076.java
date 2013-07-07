package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V076 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"teacher");
		Functions.click(tester,"Music",0);
		tester.assertMatch("Class Settings");
		Functions.click(tester,"Grades",0);
		tester.assertMatch("Date Submitted");
		IElement myCheckbox = tester.getElementByXPath("//td[text()='Harry Potter']/..//input[@type='checkbox']");
		tester.setWorkingForm("grades");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"grades","Edit");
		tester.assertMatch("Edit Grade");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"grades","Edit");
		tester.assertMatch("Edit Grade");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Vulnerabilities.selectclass(tester,"grades","Edit");
        tester.assertMatch("Edit Grade");
        tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void assignment(){
		Vulnerabilities.selectInputVulnerability(tester,"grades","Edit","assignment");
		tester.assertMatch("EditGrade.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void delete(){
		tester.assertMatch("Dewey Duck");
		Vulnerabilities.delete(tester,"grades","Edit","Harry Potter");
		tester.assertMatch("EditGrade.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
