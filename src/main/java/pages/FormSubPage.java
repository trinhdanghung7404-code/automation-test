package pages;

import org.openqa.selenium.WebElement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.WaitHelper;

public class FormSubPage {

    WebDriver driver;
    WaitHelper wait;

    By nameField = By.id("name");
    By emailField = By.id("email");
    By contactField = By.id("contact");
    By dateField = By.id("date");
    By uploadField = By.id("file");
    By colorSelect = By.cssSelector("input[type='radio']");
    By foodSelect = By.cssSelector("input[type='checkbox']");
    By countrySelect = By.id("country");
    By submitButton = By.xpath("//button[contains(text(),'Submit')]");
    
    By successTxt = By.xpath("//span[contains(text(), 'Form submit successfully.')]");
    By nameError = By.xpath("//input[@id='name']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By emailError = By.xpath("//input[@id='email']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By contactError = By.xpath("//input[@id='contact']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    By fileError = By.xpath("//input[@id='file']/parent::div/following-sibling::p");
    By colorError = By.xpath("//p[normalize-space()='Color is a required field']");
    By foodError = By.xpath("//p[normalize-space()='Food is a required field']");
    By countryError = By.xpath("//select[@id='country']/ancestor::div[contains(@class,'form-field-group')]/following-sibling::p");
    
    public FormSubPage(WebDriver driver) {
        this.driver = driver;
        wait = new WaitHelper(driver);
    }

    public void enterSubform(String name, String email, String contact, String date, String filePath, String color, List<String> foods, String country) {
		enterName(name);
		enterEmail(email);
		enterContact(contact);
		selectDate(date);
		uploadFile(filePath);
		selectColor(color);
		selectFood(foods);
		selectCountry(country);
		clickSubmit();
		}
    
    public void enterName(String name) {
        wait.waitForElementVisible(nameField);
        if(name == null || name.trim().isEmpty()) {
        	return;
        }
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        wait.waitForElementVisible(emailField);
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterContact(String contact) {
        wait.waitForElementVisible(contactField);
        driver.findElement(contactField).sendKeys(contact);
    }

    public void selectDate(String date) {
        wait.waitForElementVisible(dateField);
        driver.findElement(dateField).sendKeys(date);
    }

    public void uploadFile(String filePath) {
        wait.waitForElementVisible(uploadField);
        if(filePath == null || filePath.trim().isEmpty()) {
        	return;
        }
        driver.findElement(uploadField).sendKeys(filePath);
    }

    public void selectCountry(String country) {
        wait.waitForElementVisible(countrySelect);
        Select select = new Select(driver.findElement(countrySelect));
        select.selectByValue(country);
    }

    public void selectColor(String value){

        for(WebElement color : driver.findElements(colorSelect)){
            if(color.getAttribute("value").equals(value)){
                color.click();
                break;
            }
        }
    }

    public void selectFood(List<String> foods){

        if(foods == null || foods.isEmpty()){
            return;
        }

        for(String value : foods){

            for(WebElement food : driver.findElements(foodSelect)){

                if(food.getAttribute("value").equalsIgnoreCase(value.trim())){

                    if(!food.isSelected()){
                        food.click();
                    }

                    break;
                }
            }
        }
    }

    public void clickSubmit() {
        wait.waitForElementVisible(submitButton);
        driver.findElement(submitButton).click();
    }
    
    public boolean isSuccessTxtDisplayed() {
        wait.waitForElementVisible(successTxt);
    	return driver.findElement(successTxt).isDisplayed();
    }
    
    public String getNameError(){
        wait.waitForElementVisible(nameError);
        return driver.findElement(nameError).getText();
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

    public String getContactError(){
        wait.waitForElementVisible(contactError);
        return driver.findElement(contactError).getText();
    }

    public String getFileError(){
        wait.waitForElementVisible(fileError);
        return driver.findElement(fileError).getText();
    }

    public String getColorError(){
        wait.waitForElementVisible(colorError);
        return driver.findElement(colorError).getText();
    }

    public String getFoodError(){
        wait.waitForElementVisible(foodError);
        return driver.findElement(foodError).getText();
    }

    public String getCountryError(){
        wait.waitForElementVisible(countryError);
        return driver.findElement(countryError).getText();
    }
}