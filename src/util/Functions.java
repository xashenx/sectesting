package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.jwebunit.junit.WebTester;

public class Functions {
	public static void login(WebTester tester,String mode){
		switch (mode){
		case "admin":
			tester.beginAt("index.php");
			tester.setTextField("username", "admin");
			tester.setTextField("password", "admin");
			tester.submit();
			break;
		case "student":
			tester.beginAt("index.php");
			tester.setTextField("username", "hpotter");
			tester.setTextField("password", "potter");
			tester.submit();
			break;
		case "teacher":
			tester.beginAt("index.php");
			tester.setTextField("username", "kcobain");
			tester.setTextField("password", "cobain");
			tester.submit();
			break;
		case "parent":
			tester.beginAt("index.php");
			tester.setTextField("username", "dduck");
			tester.setTextField("password", "duck");
			tester.submit();
			break;
		default:
			break;
		} 
	}
	
    public static void click(WebTester tester,String linkname,Integer mode){
        /*
         * 0: link with text
         * 1: button
         */
        switch(mode){
            case 0:
                tester.clickLinkWithText(linkname);
                break;
            case 1:
                tester.clickButtonWithText(linkname);
                break;
        }
    }
    
	public static String[] page2Fix(String formName,String buttonOnClick){
    	String[] returnValue = new String[2];
    	returnValue[1]="";
    	String[] scriptLines = new String[4];
		String pattern1 = "page2.value=";
		String pattern2 = ";";
		Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
		Matcher m = p.matcher(buttonOnClick);
		while (m.find()) {
		  returnValue[0] = m.group(1);
		}
		String[] between = buttonOnClick.split("document." + formName + 
				".page2.value=" + returnValue[0] + ";");
		Integer scriptCounter = 0;
		Integer length = between.length;
		
		while(scriptCounter<length){
			scriptLines[scriptCounter] = between[scriptCounter];
			scriptCounter++;
		}
		int counter = 0;
		while(counter<scriptCounter){
			returnValue[1]+= scriptLines[counter++];
		}
    	return returnValue;
    }
	
	public static Integer getPage2(String linkName,String user){
		Integer value = 0;
		switch(linkName){
			case "Announcements":
				if (user.equals("teacher"))
					value = 9;
				else
					value = 4;
				break;
			case "Assignments":
				value = 2;
				break;
			case "Classes":
				if(user.equals("parent"))
					value = 5;
				else
					value = 0;
				break;
			case "Grades":
				value = 3;
				break;
			case "Parents":
				value = 22;
				break;
			case "Registration":
				value = 26;
				break;
			case "Semesters":
				value = 5;
				break;
			case "Students":
				if (user.equals("teacher"))
					value = 8;
				else if (user.equals("admin"))
					value = 2;
				else
					value = 0;
				break;
			case "Teachers":
				value = 3;
				break;
			case "Terms":
				value = 6;
				break;
			case "Users":
				value = 10;
				break;
			default:
				value = 1;
				break;
		}
		return value;
	}
	
	public static String[] deleteFix(String formName,String buttonOnClick){
    	String[] returnValue = new String[2];
    	returnValue[1]="";
    	String[] scriptLines = new String[4];
		String pattern1 = "page2.value=";
		String pattern2 = ";";
		Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
		Matcher m = p.matcher(buttonOnClick);
		while (m.find()) {
		  returnValue[0] = m.group(1);
		}
		String[] between = buttonOnClick.split("document." + formName + 
				".page2.value=" + returnValue[0] + ";");
		Integer scriptCounter = 0;
		Integer length = between.length;
		
		while(scriptCounter<length){
			scriptLines[scriptCounter] = between[scriptCounter];
			scriptCounter++;
		}
		int counter = 0;
		while(counter<scriptCounter){
			returnValue[1]+= scriptLines[counter++];
		}
    	return returnValue;
    }
	
	public static String[] onpageFix(String linkName,String linkHref){
    	String[] returnValue = new String[2];
    	returnValue[1]="";
    	String[] scriptLines = new String[4];
		String pattern1 = "onpage.value=";
		String pattern2 = ";";
		Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
		Matcher m = p.matcher(linkHref);
		while (m.find()) {
		  returnValue[0] = m.group(1);
		}
		String[] between = linkHref.split("document." + linkName + 
				".onpage.value=" + returnValue[0] + ";");
		Integer scriptCounter = 0;
		Integer length = between.length;
		
		while(scriptCounter<length){
			scriptLines[scriptCounter] = between[scriptCounter];
			scriptCounter++;
		}
		int counter = 0;
		while(counter<scriptCounter){
			returnValue[1]+= scriptLines[counter++];
		}
    	return returnValue;
    }
}
