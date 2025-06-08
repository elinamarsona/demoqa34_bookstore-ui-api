package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attach {
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes();
    }

    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    public static void browserConsoleLogs() {
        attachAsText("Browser console logs", String.join("\n", Selenide.getWebDriverLogs(BROWSER)));
    }

    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay>" +
                "<source src='https://selenoid.autotests.cloud/video/" + sessionId() + ".mp4'></video></body></html>";
    }
}