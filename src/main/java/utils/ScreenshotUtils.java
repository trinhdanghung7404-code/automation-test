package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    public static void capture(WebDriver driver, String testName) {

        try {

            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            Path screenshotDir = Paths.get("screenshots");

            // tạo folder nếu chưa có
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            Path destination = screenshotDir.resolve(testName + "_" + System.currentTimeMillis() + ".png");

            Files.copy(src.toPath(), destination);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}