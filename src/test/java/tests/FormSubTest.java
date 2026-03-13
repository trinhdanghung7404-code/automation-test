package tests;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

import base.BaseTest;
import utils.ExcelUtils;
import pages.FormSubPage;
import pages.HomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormSubTest extends BaseTest {
	
	private static final Logger logger = LogManager.getLogger(FormSubTest.class);
		
	@DataProvider(name="formsubValidData")
	public Object[][] formsubValidData(){

	    logger.info("Loading VALID form data from Excel");

	    String path = System.getProperty("user.dir")
	            + "/src/test/resources/testdata/formsubData.xlsx";
	    Object[][] data = ExcelUtils.getSheetData(path, "Sheet4");

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
	
	@Test(priority=1, dataProvider = "formsubValidData", groups = {"smoke", "regression"})
	public void formsubValid(String tcId, String name, String email, String contactNumber, String date, String filePath, String color, String food, String country ){

        logger.info("Running VALID Test Case: {}", tcId);

		HomePage homePage = new HomePage(BaseTest.getDriver());
        homePage.openFormSubmissionPage();

        FormSubPage formsubPage = new FormSubPage(BaseTest.getDriver());
        
        List<String> foodList = new ArrayList<>();

        if(food != null && !food.trim().isEmpty()){
            for(String f : food.split(",")){
                foodList.add(f.trim());
            }
        }

        formsubPage.enterSubform(name, email, contactNumber, date, filePath, color, foodList, country);
        
        logger.info("Verifying successful form");

        Assert.assertTrue(
        		formsubPage.isSuccessTxtDisplayed(),
                "Submit form success expected but failed for TC: " + tcId
        );

        logger.info("Test Passed: {}", tcId);

	}

	@DataProvider(name="formsubInvalidData")
	public Object[][] formsubInvalidData(){

        logger.info("Loading INVALID login data from Excel");
		
	    String path = System.getProperty("user.dir")
	            + "/src/test/resources/testdata/formsubData.xlsx";

	    Object[][] data = ExcelUtils.getSheetData(path, "Sheet4");

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
	
	@Test(priority=2, dataProvider = "formsubInvalidData", groups = {"regression"})
	public void formsubInvalid(String tcId, String name, String email, String contactNumber, String date, String filePath, String color, String food, String country, String errorType, String expectedMessage ){

        logger.info("Running INVALID Test Case: {}", tcId);

		HomePage homePage = new HomePage(BaseTest.getDriver());
        homePage.openFormSubmissionPage();

        FormSubPage formsubPage = new FormSubPage(BaseTest.getDriver());
        
        List<String> foodList = new ArrayList<>();

        if(food != null && !food.trim().isEmpty()){
            for(String f : food.split(",")){
                foodList.add(f.trim());
            }
        }

        formsubPage.enterSubform(name, email, contactNumber, date, filePath, color, foodList, country);
        
        String actualMessage = "";

        logger.info("Checking error type: {}", errorType);

        switch (errorType){

	        case "FIELD_NAME":
	            actualMessage = formsubPage.getNameError();
	            break;
	
	        case "FIELD_EMAIL":
	            actualMessage = formsubPage.getEmailError();
	            break;
	
	        case "FIELD_CONTACT":
	            actualMessage = formsubPage.getContactError();
	            break;
	
	        case "FIELD_FILEPATH":
	            actualMessage = formsubPage.getFileError();
	            break;
	
	        case "FIELD_COLOR":
	            actualMessage = formsubPage.getColorError();
	            break;
	
	        case "FIELD_FOOD":
	            actualMessage = formsubPage.getFoodError();
	            break;
	
	        case "FIELD_COUNTRY":
	            actualMessage = formsubPage.getCountryError();
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
