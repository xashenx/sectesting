package util;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

public class Vulnerabilities {
	public static void page(WebTester tester,String formName,String buttonName){
        IElement page = tester.getElementByXPath("//form[@name='" + formName + "']//input[@name='page']");
        String oldValue = page.getAttribute("value");
        page.setAttribute("value",oldValue +"'><a href='http://www.unitn.it'>malicious</a><br'");
        if(buttonName!=null)
        	Functions.click(tester,buttonName,1);
	}
	
	public static void page2(WebTester tester,String formName,String buttonName){
		IElement page2 = tester.getElementByXPath("//form[@name='" + formName + "']//input[@name='page2']");
		IElement button = tester.getElementByXPath("//input [@value='" + buttonName + "']");
		String onClick = button.getAttribute("onClick");
		String[] fixedValues = Functions.page2Fix(formName, onClick);
		fixedValues[0] = fixedValues[0].replace("'","");
		page2.setAttribute("value",fixedValues[0] + "'><a href='http://www.unitn.it'>malicious</a><br'");
		button.setAttribute("onClick",fixedValues[1]);
		if(buttonName!=null)
			Functions.click(tester,buttonName,1);
	}
	
	public static void page2Link(WebTester tester,String formName,String linkName,String hrefValue,String user){
		IElement page2 = tester.getElementByXPath("//form[@name='" + formName + "']//input[@name='page2']");
		if(linkName!=null){
			IElement link = tester.getElementByXPath("//a[text()='" + linkName + "']");
			link.setAttribute("href","javascript: " + hrefValue);
			Integer page2Value = Functions.getPage2(linkName,user);
			page2.setAttribute("value",page2Value + "'><a href='http://www.unitn.it'>malicious</a><br'");
			Functions.click(tester,linkName,0);
		}else{
			String page2Value = page2.getAttribute("value");
			page2.setAttribute("value",page2Value + "'><a href='http://www.unitn.it'>malicious</a><br'");
		}
	}
	
	public static void selectclass(WebTester tester,String formName,String buttonName){
        IElement selectclass = tester.getElementByXPath("//form[@name='" + formName + "']//input[@name='selectclass']");
        String oldValue = selectclass.getAttribute("value");
        selectclass.setAttribute("value",oldValue+"'><a href='http://www.unitn.it'>malicious</a><br'");
        if(buttonName!=null)
        	Functions.click(tester,buttonName,1);
	}
	
	public static void delete(WebTester tester,String formName,String buttonName,String checkBoxText){
		IElement myCheckBox = tester.getElementByXPath("//td[text()='" + checkBoxText
				+ "']/..//input[@type='checkbox']");
		String oldValue = myCheckBox.getAttribute("value");
		myCheckBox.setAttribute("value",oldValue + "';<a href=http://www.unitn.it>malicious</a>");
		tester.assertButtonPresentWithText("Edit");
		System.err.println(myCheckBox.getAttribute("value"));
		Functions.click(tester,buttonName,1);
	}
	
	public static void selectInputVulnerability(WebTester tester,String formName,String buttonName,String vulnerability){
		IElement selectInput = tester.getElementByXPath("//form[@name='" + formName +
				"']//select[@name='" + vulnerability + "']//option[@selected]");
        String oldValue = selectInput.getAttribute("value");
        selectInput.setAttribute("value",oldValue+"'><a href='http://www.unitn.it'>malicious</a><br /'");
        if(buttonName!= null)
        	Functions.click(tester,buttonName,1);
	}
	
	public static void textFieldVulnerability(WebTester tester,
			String formName, String fieldName,String buttonName) {
//		String oldValue = tester.getElementByXPath("//input [@name='" + fieldName + "']").getAttribute("value");
		tester.setTextField(fieldName,"<a href>mallink</a>");
		Functions.click(tester,buttonName, 1);
	}
	
	public static void onpage(WebTester tester,String linkName,String formName){
		IElement link = tester.getElementByXPath("//a[text()='" + linkName + "']");
		link.setAttribute("href","JavaScript: document." + formName + ".submit();");
		IElement onpage = tester.getElementByXPath("//form[@name='" + formName + "']" +
				"//input[@name='onpage']");
        String oldValue = onpage.getAttribute("value");
        onpage.setAttribute("value",oldValue +"'><a href='http://www.unitn.it'>malicious</a><br'");
	}
}
