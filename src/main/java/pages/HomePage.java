package pages;

import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    String baseUrl = "https://practice.qabrains.com";

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public void openHomePage(){
        driver.get(baseUrl);
    }

    public void openLoginPage(){
        driver.get(baseUrl);
    }

    public void openRegisterPage(){
        driver.get(baseUrl + "/registration");
    }

    public void openForgotPasswordPage(){
        driver.get(baseUrl + "/forgot-password");
    }

    public void openFormSubmissionPage(){
        driver.get(baseUrl + "/form");
    }
}
