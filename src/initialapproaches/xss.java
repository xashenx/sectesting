/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package initialapproaches;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.*;
import util.Functions;

/**
 *
 * @author ashen
 */
public class xss {
    private static WebTester tester;
    public xss() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost/sm/");
    }
    
    @AfterClass
    public static void tearDownClass() {
        tester.beginAt("index.php");
        Functions.login(tester, "admin");
        tester.submit();
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        tester.setTextField("sitetext", "Nice sitetext");
        tester.setTextField("schooladdress", "1,Nice address");
        Functions.click(tester," Update ",1);
        Functions.click(tester,"Log Out",0);
        tester = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    private void login(String mode){
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
    
    private void click(String linkname,Integer mode){
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

    @Test
    public  void siteText(){
    	Functions.login(tester,"admin");
        click("School",0);
        tester.assertMatch("Manage School Information");
        tester.setTextField("sitetext", "<a href=\"http://www.unitn.zt\">maliciousSiteText</a>");
        Functions.click(tester," Update ",1);
        Functions.click(tester,"Log Out",0);
        tester.assertLinkNotPresentWithText("maliciousSiteText");
    }

    @Test
    public void address(){
        Functions.login(tester,"admin");
        Functions.click(tester,"School",0);
        tester.assertMatch("Manage School Information");
        tester.setTextField("schooladdress", "<a href=\"http://www.unitn.at\">maliciousAddress</a>");
        Functions.click(tester," Update ",1);
        tester.assertNoMatch("<a href=\"http://www.unitn.at\">maliciousAddress</a>");
        Functions.click(tester,"Log Out",0);
    }
    
    @Test
    public void assignmentInfo(){
        Functions.login(tester,"teacher");
        Functions.click(tester,"Music",0);
        tester.assertMatch("Class Settings");
        Functions.click(tester,"Assignments",0);
        tester.assertMatch("Manage Assignments");
        tester.assertMatch("verifica di prova");
        Functions.click(tester,"Add",1);
        tester.assertMatch("Add New Assignment");
        tester.setTextField("title", "Malicious Test");
        tester.setTextField("task", "<a href=\"http://www.unitn.it\">maliciousAssignmentInfo</a>");
        tester.setTextField("total", "5");
        tester.setTextField("assigneddate", "12-12-12");
        tester.setTextField("duedate", "12-12-12");
//        Functions.click(tester,"Add Assignment",1);
        Functions.click(tester,"Assignments",0);
        tester.assertLinkNotPresentWithText("maliciousAssignmentInfo");
        System.out.println("barabba: " + tester.getElementByXPath("//input [@type='checkbox']").getTextContent());
        Functions.click(tester,"Log Out",0);
//      IElement button = tester.getElementByXPath("//input [@value=' Update ']");
//      System.err.println("El botttooonnn" + button);
        
    }
}
