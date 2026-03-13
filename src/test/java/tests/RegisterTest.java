package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

import pages.HomePage;
import pages.RegisterPage;
import utils.ExcelUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(RegisterTest.class);
	
	@DataProvider(name = "registerValidData")
	public Object[][] registerValidData(){
		logger.info("Loading VALID register data from Excel");

		String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/registerData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet2");
        
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
	
	@Test(priority = 1, dataProvider = "registerValidData", groups = {"smoke", "regression"})
	public void registerValid(String tcId, String name, String country, String accountType, String email, String password, String confirmPassword){

        logger.info("Running VALID Test Case: {}", tcId);

    	HomePage homePage = new HomePage(BaseTest.getDriver());
        logger.info("Opening Register Page");
        homePage.openRegisterPage();

        RegisterPage registerPage = new RegisterPage(BaseTest.getDriver());
        
        registerPage.register(name, country,accountType, email, password, confirmPassword);
        
        logger.info("Verifying successful register");

        Assert.assertTrue(
                registerPage.isSuccessTxtDisplayed(),
                "Register success expected but failed for TC: " + tcId
        );

        logger.info("Test Passed: {}", tcId);
    }
	
	@DataProvider(name = "registerInvalidData")
	public Object[][] registerInvalidData(){
		logger.info("Loading INVALID register data from Excel");

		String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/registerData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet2");
		
        logger.info("Excel loaded successfully");
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

	@Test(priority = 2, dataProvider = "registerInvalidData", groups = {"regression"})
	public void registerInvalid(String tcId, String name, String country, String accountType, String email, String password, String confirmPassword, String errorType, String expectedMessage){

	    logger.info("Running INVALID Test Case: {}", tcId);

		HomePage homePage = new HomePage(BaseTest.getDriver());
	    logger.info("Opening Register Page");
	    homePage.openRegisterPage();

	    RegisterPage registerPage = new RegisterPage(BaseTest.getDriver());
	    
	    registerPage.register(name, country,accountType, email, password, confirmPassword);
	    
	    String actualMessage = "";

        logger.info("Checking error type: {}", errorType);

        switch (errorType){

            case "FIELD_NAME":
                actualMessage = registerPage.getNameError();
                break;

            case "FIELD_COUNTRY":
                actualMessage = registerPage.getCountryError();
                break;

            case "FIELD_ACCOUNT":
                actualMessage = registerPage.getAccountError();
                break;
            case "FIELD_EMAIL":
                actualMessage = registerPage.getEmailError();
                break;

            case "FIELD_PASSWORD":
                actualMessage = registerPage.getPasswordError();
                break;

            case "FIELD_CONFIRMPASSWORD":
                actualMessage = registerPage.getConfirmPasswordError();
                break;

            default:
                logger.warn("Unknown error type: {}", errorType);
        }

        logger.info("Expected error message: {}", expectedMessage);
        logger.info("Actual error message: {}", actualMessage);
        
        Assert.assertEquals(actualMessage.trim(), expectedMessage.trim());

	    logger.info("Test Passed: {}", tcId);
	}
}


