package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitHelper;

public class HomePage {

    WebDriver driver;
    WaitHelper wait;

    By loginBtn = By.linkText("Login");
    By registerBtn = By.linkText("Register");
    By forgotPassBtn = By.linkText("Forgot Password");
    By formSubmissionBtn = By.linkText("Form Submission");

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    private void clickElement(By locator){

        WebElement element = wait.waitForElementClickable(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        try{
            element.click();
        }
        catch(Exception e){
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    public void openLoginPage(){
        clickElement(loginBtn);
    }

    public void openRegisterPage(){
        clickElement(registerBtn);
    }

    public void openForgotPasswordPage(){
        clickElement(forgotPassBtn);
    }

    public void openFormSubmissionPage(){
        clickElement(formSubmissionBtn);
    }
}
