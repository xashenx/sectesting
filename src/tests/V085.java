package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V085 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"admin");
		Functions.click(tester,"Semesters",0);
		tester.assertMatch("Manage Semesters");
		IElement myCheckbox = tester.getElementByXPath("//td[text()='first']/..//input[@type='checkbox']");
		tester.setWorkingForm("semesters");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"semesters","Edit");
		tester.assertMatch("Edit Semester");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"semesters","Edit");
		tester.assertMatch("Edit Semester");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void delete(){
		Vulnerabilities.delete(tester,"semesters","Edit","first");
		tester.assertMatch("EditSemester.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
