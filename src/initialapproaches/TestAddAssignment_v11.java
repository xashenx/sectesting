package initialapproaches;

import net.sourceforge.jwebunit.api.*;
import net.sourceforge.jwebunit.htmlunit.*;
import net.sourceforge.jwebunit.junit.*;

import org.junit.*;
import org.junit.Test;
import org.xml.sax.helpers.*;

import com.gargoylesoftware.htmlunit.html.*;

public class TestAddAssignment_v11 {

	private WebTester tester;
	
	@Before
	public void prepare(){
		tester = new WebTester();
		tester.setBaseUrl("http://localhost/sm/");
	}
	
	@Test
	public void testVulnerability11(){
		tester.beginAt("index.php");
		tester.assertMatch("Today's Message");
		
		tester.setTextField("username","kcobain");
		tester.setTextField("password","cobain");
		tester.submit();
		
//		tester.assertMatch("Mariano Ceccato's Classes");
		tester.clickLinkWithText("Music");
		
		tester.assertMatch("Class Settings");
		tester.clickLinkWithText("Assignments");
		
		tester.assertMatch("Manage Assignments");
		tester.setTextField("page2","4 '> <a href=www.google.com>malicious link</a> <br / '");
		System.err.println(tester.getElementByXPath("//input [@name='page2']"));
		addSubmitButton("//form[@name='teacher']");
		tester.submit();

		tester.assertMatch("Add New Assignment");
		tester.assertLinkNotPresentWithText("malicious link");
	}


	private void addSubmitButton(String expression) {
		IElement element = tester.getElementByXPath(expression);
		DomElement form = ((HtmlUnitElementImpl)element).getHtmlElement();
		InputElementFactory factory = InputElementFactory.instance; 
		AttributesImpl attributes = new AttributesImpl();
		attributes.addAttribute("", "", "type", "", "submit");
		HtmlElement submit = factory.createElement(form.getPage(), "input", attributes);
		form.appendChild(submit);
	}
	

}

