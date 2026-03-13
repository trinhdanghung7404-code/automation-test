package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        this.wait = new WaitHelper(driver);
    }

    private void clickElement(By locator){

        WebElement element = wait.waitForElementClickable(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);

        element.click();
    }

    private void switchToNewWindow(){

        String currentWindow = driver.getWindowHandle();

        for(String window : driver.getWindowHandles()){
            if(!window.equals(currentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void openLoginPage(){
        clickElement(loginBtn);
        switchToNewWindow();
    }

    public void openRegisterPage(){
        clickElement(registerBtn);
        switchToNewWindow();
    }

    public void openForgotPasswordPage(){
        clickElement(forgotpassBtn);
        switchToNewWindow();
    }

    public void openFormSubmissionPage(){
        clickElement(formsubBtn);
        switchToNewWindow();
    }
}