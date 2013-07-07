package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.MySql;
import util.Vulnerabilities;

public class V030 {
	private static WebTester tester;

	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester, "student");
		Functions.click(tester, "Music", 0);
		tester.assertMatch("Class Settings");
	}

	@Test
	public void coursename() {
		Functions.click(tester, "Log Out", 0);
		tester.assertMatch("TutttoBBBene");
		// INJECTING A LINK IN THE COURSENAME
		Functions.login(tester, "admin");
		Functions.click(tester, "Classes", 0);
		tester.assertMatch("Manage Classes");
		IElement myCheckbox = tester
				.getElementByXPath("//td[text()='Kurt Cobain']/..//input[@type='checkbox']");
		tester.setWorkingForm("classes");
		tester.checkCheckbox("delete[]", myCheckbox.getAttribute("value"));
		Functions.click(tester, "Edit", 1);
		tester.assertMatch("Music");
		tester.assertMatch("Edit Class");
		Vulnerabilities.textFieldVulnerability(tester, "editclass", "title",
				"Edit Class");
//		tester.assertLinkPresentWithText("malicious");
		Functions.click(tester, "Log Out", 0);
		// CHECKING THE VULNERABILITY
		Functions.login(tester, "student");
		// NEED TO ACCESS THROUGH ANOTHER CLASS, BECAUSE THE LINK DOES NOT LET US TO CONTINUE
		Functions.click(tester, "Gymnastic", 0);
		IElement select = tester.getElementByXPath("//form[@name='student']//input[@name='selectclass']");
//		System.err.println(select.getAttribute("value"));
		select.setAttribute("value","1"); // THE ID OF THE DESIRED CLASS
//		System.err.println(select.getAttribute("value"));
		tester.assertMatch("Class Settings");
		Functions.click(tester, "Assignments", 0);
		tester.assertMatch("View Assignments");
		tester.assertLinkNotPresentWithExactText("mallink");
	}
	
	@Test
	public void coursenameSQL(){
		MySql.executeUpdate("UPDATE courses SET coursename = 'Music<a href>mal</a>' WHERE courseid = '1'");
		Functions.click(tester, "Assignments", 0);
		tester.assertMatch("View Assignments");
		tester.assertLinkNotPresentWithExactText("mal");
	}
	
	@Test
	public void assignmentInformation(){
		MySql.executeUpdate("UPDATE assignments SET assignmentinformation = '<a href>malicious</a>' WHERE assignmentid = '3'");
		Functions.click(tester, "Assignments", 0);
		tester.assertMatch("View Assignments");
		tester.assertLinkNotPresentWithExactText("malicious");
	}

	@After
	public void cleanup() {
		Functions.click(tester, "Log Out", 0);
		// BEGIN COURSENAME CLEANUP
		Functions.login(tester, "admin");
		Functions.click(tester, "Classes", 0);
		tester.assertMatch("Manage Classes");
		IElement myCheckbox = tester
				.getElementByXPath("//td[text()='Kurt Cobain']/..//input[@type='checkbox']");
		tester.setWorkingForm("classes");
		tester.checkCheckbox("delete[]", myCheckbox.getAttribute("value"));
		Functions.click(tester, "Edit", 1);
		tester.assertMatch("Edit Class");
		tester.setTextField("title","Music");
		Functions.click(tester,"Edit Class", 1);
		Functions.click(tester, "Log Out", 0);
		// END COURSENAME CLEANUP
		MySql.executeUpdate("UPDATE assignments SET assignmentinformation = 'Verifica' WHERE assignmentid = '3'");
		tester = null;
	}
}
