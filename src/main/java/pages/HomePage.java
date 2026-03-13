package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitHelper;

public class HomePage {

    WebDriver driver;
    WaitHelper wait;

    By loginBtn = By.cssSelector("a[href='https://practice.qabrains.com/']");
    By registerBtn = By.cssSelector("a[href='https://practice.qabrains.com/registration']");
    By forgotpassBtn = By.cssSelector("a[href='https://practice.qabrains.com/forgot-password']");
    By formsubBtn = By.cssSelector("a[href='https://practice.qabrains.com/form-submission']");

    public HomePage(WebDriver driver){
        this.driver = driver;
        wait = new WaitHelper(driver);
    }

    private void switchToNewWindow(){
        for(String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
        }
    }

    public void openLoginPage(){
        wait.waitForElementVisible(loginBtn);
        driver.findElement(loginBtn).click();
        switchToNewWindow();
    }

    public void openRegisterPage(){
        wait.waitForElementVisible(registerBtn);
        driver.findElement(registerBtn).click();
        switchToNewWindow();
    }

    public void openForgotPasswordPage(){
        wait.waitForElementVisible(forgotpassBtn);
        driver.findElement(forgotpassBtn).click();
        switchToNewWindow();
    }

    public void openFormSubmissionPage(){
        wait.waitForElementVisible(formsubBtn);
        driver.findElement(formsubBtn).click();
        switchToNewWindow();
    }

}