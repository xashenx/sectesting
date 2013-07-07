package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V299 {
	private static WebTester tester;

	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
		tester.beginAt("index.php");
		Functions.login(tester, "admin");
		tester.assertMatch("Manage Classes");
	}

	@Test
	public void page() {
		Vulnerabilities.page(tester, "admin", null);
		Functions.click(tester, "Registration", 0);
		tester.assertMatch("Period Number");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@Test
	public void page2() {
		Vulnerabilities.page2Link(tester, "admin", "Registration",
				"document.admin.submit();","admin");
		tester.assertMatch("Period Number");
		tester.assertLinkNotPresentWithText("malicious");
	}

	@After
	public void cleanup() {
		Functions.click(tester, "Log Out", 0);
		tester = null;
	}
}
