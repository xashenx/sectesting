package tests;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V041 {
	private static WebTester tester;

	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester,"admin");
		Functions.click(tester,"Announcements",0);
		tester.assertMatch("Manage Announcements");
		IElement myCheckbox = tester.getElementByXPath("//td[text()='myannouncement']/..//input[@type='checkbox']");
		tester.setWorkingForm("announcements");
		tester.checkCheckbox("delete[]",myCheckbox.getAttribute("value"));
	}

	@Test
	public void page(){
		Vulnerabilities.page(tester,"announcements","Edit");
		tester.assertMatch("Edit Announcement");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2(){
		Vulnerabilities.page2(tester,"announcements","Edit");
		tester.assertMatch("Edit Announcement");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void delete(){
		Vulnerabilities.delete(tester,"announcements","Edit","myannouncement");
		tester.assertMatch("EditAnnouncement.php: Unable to retrieve");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
