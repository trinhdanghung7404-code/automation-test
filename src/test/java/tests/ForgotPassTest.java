package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import base.BaseTest;
import pages.ForgotPassPage;
import pages.HomePage;
import utils.ExcelUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ForgotPassTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(ForgotPassTest.class);

	@DataProvider(name = "forgotValidData")
	public Object[][] forgotValidData(){
		logger.info("Loading VALID fogot data from Excel");

		String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/forgotData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet3");
        
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
	@Test(priority =1,dataProvider = "forgotValidData", groups = {"smoke", "regression"})
	public void forgotValidData(String tcId, String email) {
        
		logger.info("Running VALID Test Case: {}", tcId);

		HomePage homePage = new HomePage(BaseTest.getDriver());
        homePage.openForgotPasswordPage();

        ForgotPassPage forgotpassPage = new ForgotPassPage(BaseTest.getDriver());

        forgotpassPage.resetPass(email);
        
        logger.info("Verifying successful forgot email");
        
        Assert.assertTrue(
        		forgotpassPage.isSuccessTxtDisplayed(),
                "Register success expected but failed for TC: " + tcId
        );
	}
	
	@DataProvider(name = "forgotInvalidData")
	public Object[][] forgotInvalidData(){
		logger.info("Loading INVALID forgot data from Excel");

		String path = System.getProperty("user.dir")
		        + "/src/test/resources/testdata/forgotData.xlsx";
		Object[][] data = ExcelUtils.getSheetData(path, "Sheet3");
        
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
	@Test(priority =2, dataProvider = "forgotInvalidData", groups = {"smoke", "regression"})
	public void forgotInvalidData(String tcId, String email, String errorType, String expectedMessage) {
        
		logger.info("Running INVALID Test Case: {}", tcId);

		HomePage homePage = new HomePage(BaseTest.getDriver());
        homePage.openForgotPasswordPage();

        ForgotPassPage forgotpassPage = new ForgotPassPage(BaseTest.getDriver());

        forgotpassPage.resetPass(email);
        
	    String actualMessage = "";
        
        logger.info("Checking error type: {}", errorType);
	    
        switch (errorType){

        case "FIELD_EMAIL":
            actualMessage = forgotpassPage.getEmailError();
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
