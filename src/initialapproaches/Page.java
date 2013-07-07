package initialapproaches;
import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import util.Functions;
public class Page {
	private static WebTester tester;
    
	@Before
	public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
        tester.beginAt("index.php");
	}
	
	@Test
	public void page(){
        Functions.login(tester,"teacher");
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
        Functions.click(tester,"Assignments",0);
        tester.assertMatch("Manage Assignments");
        IElement page = tester.getElementByXPath("//form [@name='assignments']//input [@name='page']");
        page.setAttribute("value","2'><a href='http://www.unitn.it'>malicious</a><br'");
        Functions.click(tester,"Add",1);
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious");
	}
}
