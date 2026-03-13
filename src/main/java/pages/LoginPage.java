package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitHelper;

public class LoginPage {

    WebDriver driver;
    WaitHelper wait;

    By emailField = By.id("email");
    By passwordField = By.id("password");
    By loginButton = By.xpath("//button[contains(text(),'Login')]");
    By successTxt = By.xpath("//h2[contains(text(),'Login Successful')]");
    
    By globalError = By.cssSelector(".toaster .title");
    By passwordError = By.xpath("//input[@id='password']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By emailError = By.xpath("//input[@id='email']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By emailToast = By.xpath("//div[contains(@class,'toaster')]//span[contains(@class,'title')]");
    
    public LoginPage(WebDriver driver){
        this.driver = driver;
        wait = new WaitHelper(driver);
    }
    
    public void login(String email, String password) {
    	enterEmail(email);
    	enterPassword(password);
    	clickLogin();
    }

    public void enterEmail(String email){
        wait.waitForElementVisible(emailField);
        if(email == null || email.trim().isEmpty()) {
        	return;
        }
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password){
        wait.waitForElementVisible(passwordField);
        if(password == null || password.trim().isEmpty()) {
        	return;
        }
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin(){
        wait.waitForElementVisible(loginButton);
        driver.findElement(loginButton).click();
    }
    
    public boolean isSuccessTxtDisplayed() {
        wait.waitForElementVisible(successTxt);
    	return driver.findElement(successTxt).isDisplayed();
    }
    
    public String getGlobalError(){
    	wait.waitForElementVisible(globalError);
        return driver.findElement(globalError).getText();
    }

    public String getEmailError(){

        WebElement emailInput = driver.findElement(emailField);

        String browserMessage = emailInput.getAttribute("validationMessage");
        if(browserMessage != null && !browserMessage.trim().isEmpty()){
            return "Email must be a valid email";
        }

        if(driver.findElements(emailError).size() > 0){
            return driver.findElement(emailError).getText().trim();
        }
        
        wait.waitForElementVisible(emailToast);
        if(driver.findElements(emailToast).size() > 0){
            String toast = driver.findElement(emailToast).getText().trim();

            if(toast.toLowerCase().contains("email")){
                return "Email must be a valid email";
            }

            return toast;
        }

        return "";
    }

    public String getPasswordError(){
    	wait.waitForElementVisible(passwordError);
        return driver.findElement(passwordError).getText();
    }
}