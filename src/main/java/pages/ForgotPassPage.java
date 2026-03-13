package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitHelper;

public class ForgotPassPage {
	WebDriver driver;
    WaitHelper wait;
    
    By emailField = By.id("email");
    By resetpassButton = By.xpath("//button[contains(text(),'Reset Password')]");
    By successTxt = By.xpath("//span[contains(text(), 'Password is reset successfully.')]");
    By emailError = By.xpath("//input[@id='email']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");


    public ForgotPassPage(WebDriver driver) {
    	this.driver = driver;
        wait = new WaitHelper(driver);
    }
    
    public void resetPass(String email) {
    	enterEmail(email);
    	clickResetPass();
    }
    
    public void enterEmail(String email) {
    	wait.waitForElementVisible(emailField);
        driver.findElement(emailField).sendKeys(email);
    }
    
    public void clickResetPass(){
        wait.waitForElementVisible(resetpassButton);
        driver.findElement(resetpassButton).click();
    }
    
    public boolean isSuccessTxtDisplayed() {
        wait.waitForElementVisible(successTxt);
    	return driver.findElement(successTxt).isDisplayed();
    }
    
    public String getEmailError(){

        WebElement emailInput = driver.findElement(emailField);

        String browserMessage = emailInput.getAttribute("validationMessage");

        if(browserMessage != null && !browserMessage.isEmpty()){
            return "Email must be a valid email";
        }

        wait.waitForElementVisible(emailError);
        return driver.findElement(emailError).getText();
    }
    
}
