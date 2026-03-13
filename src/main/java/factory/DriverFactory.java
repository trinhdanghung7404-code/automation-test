package factory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver initDriver(){

        ChromeOptions options = new ChromeOptions();

        // cấu hình cho CI
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);

        // tắt implicit wait (dùng explicit wait)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        return driver;
    }
}
