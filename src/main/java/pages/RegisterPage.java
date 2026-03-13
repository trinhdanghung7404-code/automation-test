package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.WaitHelper;

public class RegisterPage {

    WebDriver driver;
    WaitHelper wait;
    
    By nameField = By.id("name");
    By countrySelect = By.id("country");
    By accounttypeSelect = By.id("account");
    By emailField = By.id("email");
    By passwordField = By.id("password");
    By confirmpasswordField = By.id("confirm_password");
    By registerButton = By.xpath("//button[contains(text(),'Signup')]");
    By successTxt = By.xpath("//span[contains(text(), 'Registration Successful')]");
    
    By nameError = By.xpath("//input[@id='name']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By countryError = By.xpath("//select[@id='country']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By accountError = By.xpath("//select[@id='account']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By emailError = By.xpath("//input[@id='email']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By passwordError = By.xpath("//input[@id='password']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By confirmPasswordError = By.xpath("//input[@id='confirm_password']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");


	public RegisterPage(WebDriver driver){
	        this.driver = driver;
	        wait = new WaitHelper(driver);
	}
	
	public void register(String name, String country, String accountType, String email, String password, String confirmPassword) {
		enterName(name);
		selectCountry(country);
		selectAccounttype(accountType);
		enterEmail(email);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickSignup();
	}
	
	public void enterName(String name){
        wait.waitForElementVisible(nameField);
        driver.findElement(nameField).sendKeys(name);
    }
	
	public void selectCountry(String value){
        wait.waitForElementVisible(countrySelect);
        if(value == null || value.trim().isEmpty()) {
        	return;
        }
		Select select = new Select(driver.findElement(countrySelect));
		select.selectByValue(value);
	}
	
	public void selectAccounttype(String value){
        wait.waitForElementVisible(accounttypeSelect);
        if(value == null || value.trim().isEmpty()) {
        	return;
        }
		Select select = new Select(driver.findElement(accounttypeSelect));
		select.selectByValue(value);
	}
	
	public void enterEmail(String email){
        wait.waitForElementVisible(emailField);
        driver.findElement(emailField).sendKeys(email);
    }
	
	public void enterPassword(String password){
        wait.waitForElementVisible(passwordField);
        driver.findElement(passwordField).sendKeys(password);
    }

	public void enterConfirmPassword(String password){
        wait.waitForElementVisible(confirmpasswordField);
        driver.findElement(confirmpasswordField).sendKeys(password);
    }
	
    public void clickSignup(){
        driver.findElement(registerButton).click();
    }
    
    public boolean isSuccessTxtDisplayed() {
        wait.waitForElementVisible(successTxt);
    	return driver.findElement(successTxt).isDisplayed();
    }
    
    public String getNameError(){
        wait.waitForElementVisible(nameError);
        return driver.findElement(nameError).getText();
    }
    
    public String getCountryError(){
        wait.waitForElementVisible(countryError);
    	return driver.findElement(countryError).getText();
    }
    
    public String getAccountError(){
    	wait.waitForElementVisible(accountError);
    	return driver.findElement(accountError).getText();
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
    
    public String getConfirmPasswordError(){
        wait.waitForElementVisible(confirmPasswordError);
        return driver.findElement(confirmPasswordError).getText();
    }
    
    public String getPasswordError(){
    	wait.waitForElementVisible(passwordError);
        return driver.findElement(passwordError).getText();
    }
    
}
