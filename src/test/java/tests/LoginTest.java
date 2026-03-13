package tests;

import base.BaseTest;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @DataProvider(name = "loginValidData")
    public Object[][] loginValidData(){
        logger.info("Loading VALID login data from Excel");

        String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/loginData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet1");

        List<Object[]> validData = new ArrayList<>();

        int typeIndex = data[0].length - 1;  
	    int usableCols = data[0].length - 3;  

	    for(Object[] row : data){

	        String type = row[typeIndex].toString();

	        if("VALID".equalsIgnoreCase(type)){

	            Object[] filtered = new Object[usableCols];

	            for(int i = 0; i < usableCols; i++){
	                filtered[i] = row[i];
	            }

	            validData.add(filtered);
	        }
	    }

        logger.info("Total VALID test cases loaded: {}", validData.size());

        return validData.toArray(new Object[0][]);
    }


    @Test(priority=1, dataProvider = "loginValidData", groups = {"smoke","regression"})
    public void loginValidAccount(String tcId, String email, String password){

        logger.info("Running VALID Test Case: {}", tcId);

        HomePage homePage = new HomePage(BaseTest.getDriver());
        logger.info("Opening Login Page");
        homePage.openLoginPage();

        LoginPage loginPage = new LoginPage(BaseTest.getDriver());
        
        logger.info("Attempting login with email : password: {} {}", email, password);
        loginPage.login(email, password);
        
        logger.info("Verifying successful login");

        Assert.assertTrue(
                loginPage.isSuccessTxtDisplayed(),
                "Login success expected but failed for TC: " + tcId
        );

        logger.info("Test Passed: {}", tcId);
    }


    @DataProvider(name = "loginInvalidData")
    public Object[][] loginInvalidData(){

        logger.info("Loading INVALID login data from Excel");

        String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/loginData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet1");

        List<Object[]> invalidData = new ArrayList<>();

        int typeIndex = data[0].length - 1;

	    for(Object[] row : data){

	        if("INVALID".equalsIgnoreCase(row[typeIndex].toString().trim())){

	            Object[] filtered = new Object[row.length - 1];
	            System.arraycopy(row, 0, filtered, 0, row.length - 1);

	            invalidData.add(filtered);
	        }
	    }

        logger.info("Total INVALID test cases loaded: {}", invalidData.size());

        return invalidData.toArray(new Object[0][]);
    }

    @Test(priority=2, dataProvider = "loginInvalidData", groups = {"regression"})
    public void loginInvalidCases(String tcId, String email, String password, 
    								String expectedMessage, String errorType){

        logger.info("Running INVALID Test Case: {}", tcId);

        HomePage homePage = new HomePage(BaseTest.getDriver());
        logger.info("Opening Login Page");
        homePage.openLoginPage();

        LoginPage loginPage = new LoginPage(BaseTest.getDriver());

        logger.info("Attempting login with email : password: {} {}", email, password);
        loginPage.login(email,password);

        String actualMessage = "";

        logger.info("Checking error type: {}", errorType);

        switch (errorType){

            case "GLOBAL":
                actualMessage = loginPage.getGlobalError();
                break;

            case "FIELD_EMAIL":
                actualMessage = loginPage.getEmailError();
                break;

            case "FIELD_PASSWORD":
                actualMessage = loginPage.getPasswordError();
                break;

            default:
                logger.warn("Unknown error type: {}", errorType);
        }

        logger.info("Expected error message: {}", expectedMessage);
        logger.info("Actual error message: {}", actualMessage);

        Assert.assertEquals(actualMessage, expectedMessage);

        logger.info("Test Passed: {}", tcId);
    }
}