package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.MySql;

public class V234 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"admin");
        Functions.click(tester,"Terms",0);
        tester.assertMatch("Manage Terms");
		IElement myCheckbox = tester.getElementByXPath("//td[text()='09/10/2012']/..//input[@type='checkbox']");
		tester.setWorkingForm("terms");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
		Functions.click(tester,"Edit",1);
		tester.setTextField("title","<a href>a</a>");
		Functions.click(tester,"Edit Term",1);
	}
	
	@Test
	public void term(){
		Functions.click(tester,"Semesters",0);
        tester.assertMatch("Manage Semesters");
        tester.assertLinkNotPresentWithExactText("a");
	}
	
	@Test
	public void termSQL(){
		MySql.executeUpdate("UPDATE terms SET" +
					"`title`= '<a href>a</a>'");
		Functions.click(tester,"Semesters",0);
        tester.assertMatch("Manage Semesters");
		tester.assertLinkNotPresentWithExactText("a");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Terms",0);
		IElement myCheckbox = tester.getElementByXPath("//td[text()='09/10/2012']/..//input[@type='checkbox']");
		tester.setWorkingForm("terms");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
		Functions.click(tester,"Edit",1);
		tester.setTextField("title","2012-2013");
		Functions.click(tester,"Edit Term",1);
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
