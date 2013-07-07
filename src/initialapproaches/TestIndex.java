package initialapproaches;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestIndex {
	private WebTester tester;

	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
	}
	
	@Test
	public void test15() {
		prepare();
		tester.beginAt("index.php");
		tester.assertMatch("Today's Message");
		// tester.assertMatch("Guess the number");
		tester.setTextField("username", "admin");
		tester.setTextField("password", "admin");
		tester.submit();
		tester.assertMatch("Manage Classes");
		tester.clickLinkWithText("School");
		tester.assertMatch("Manage School Information");
		tester.setTextField("sitetext",
				"<a href=\"http://www.unitn.it\">malicious</a>");
		tester.clickButtonWithText(" Update ");
		// tester.assertMatch("Wrong");
	}

	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
