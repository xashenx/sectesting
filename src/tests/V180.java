package tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Functions;
import util.Vulnerabilities;

public class V180 {
	private static WebTester tester;
	
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
        Functions.login(tester,"teacher");
        tester.assertMatch("Kurt Cobain's Classes");
	}
	
	@Test
	public void page(){
		Vulnerabilities.page(tester,"teacher",null);
		Functions.click(tester,"Classes",0);
        tester.assertMatch("Kurt Cobain's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void page2(){
		Vulnerabilities.page2Link(tester,"teacher","Classes","document.teacher.submit();","teacher");
        tester.assertMatch("Kurt Cobain's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@Test
	public void selectclass(){
		Vulnerabilities.selectclass(tester,"teacher",null);
		Functions.click(tester,"Classes",0);
        tester.assertMatch("Kurt Cobain's Classes");
        tester.assertLinkNotPresentWithText("malicious");
	}
	
	@After
	public void cleanup(){
		Functions.click(tester,"Log Out",0);
		tester = null;
	}
}
