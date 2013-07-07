package initialapproaches;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;

public class ManAss {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"teacher");
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
        Functions.click(tester,"Assignments",0);
        tester.assertMatch("Manage Assignments");
        tester.assertMatch("verifica di prova");
	}
	
	@Test
	public void assignment(){
        Functions.click(tester,"Add",1);
        tester.assertMatch("Add New Assignment");
        tester.setTextField("title", "Malicious Test");
        tester.setTextField("task", "<a href=\"http://www.unitn.it\">maliciousAssignmentInfo</a>");
        tester.setTextField("total", "5");
        tester.setTextField("assigneddate", "12-12-12");
        tester.setTextField("duedate", "12-12-12");
        Functions.click(tester,"Add Assignment",1);
        tester.assertMatch("Music");
        tester.assertLinkNotPresentWithText("maliciousAssignmentInfo");
	}
	
	@After
	public void cleanup(){
        IElement myCheckbox = tester.getElementByXPath("//td[text()='Malicious Test']/..//input[@type='checkbox']");
        tester.setWorkingForm("assignments");
        tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
        tester.assertButtonPresentWithText("Delete");
        IElement button = tester.getElementByXPath("//input[@value='Delete']");
        button.setAttribute("onClick","document.assignments.deleteassignment.value=1;document.assignments.submit();");
        tester.clickButtonWithText("Delete");
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
