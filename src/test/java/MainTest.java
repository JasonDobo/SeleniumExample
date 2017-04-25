import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by jason.dobo on 24/04/2017.
 */
public class MainTest {

    WebDriver driver;
    private int DEFAULT_TIMEOUT = 10;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/jasondobo/Downloads/chromedriver");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testOne() throws InterruptedException {
        driver.get("https://www.moo.com/uk/");
        handleNewsLetterOverlay();

        WebElement searchBar = waitForElement(By.id("query"), DEFAULT_TIMEOUT);
        searchBar.click();
        searchBar.sendKeys("business cards");

        WebElement searchButton = waitForElement(By.className("search__btn"), DEFAULT_TIMEOUT);
//        WebElement searchButton = driver.findElement(By.cssSelector("input[class='search__btn u-p0 js-search-btn']"));
        searchButton.click();

        List<WebElement> jaso = driver.findElements(By.className("close"));

        String attribute = jaso.get(0).getAttribute("data-webdriver-automation-id");
        jaso.get(0).click();

        WebElement jason = driver.findElement(By.id("newsletter-overlay-close-link"));
        waitForElement(By.id("newsletter-overlay-close-link"), 30);



        fluentWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("newsletter-overlay-close-link")));



        System.out.println("Successfully opened the website www.google.com");
        WebElement element = driver.findElement(By.id("lst-ib"));
        Assert.assertTrue(element.isDisplayed());
        element.sendKeys("Test");
        element.sendKeys(Keys.RETURN);


        Wait wait = new FluentWait(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        ExpectedCondition<WebElement> elementExpectedCondition  = ExpectedConditions.visibilityOfElementLocated(By.id("lst-ib"));
        wait.until(elementExpectedCondition);

        Thread.sleep(5);
        driver.quit();
    }

    private Wait<WebDriver> fluentWait(long timeout) {
        return new FluentWait<>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    private WebElement waitForElement(By by, int seconds) {
        WebElement element;
        try {
            element = fluentWait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Try find element " + by.toString() + ", not found");
            throw new NoSuchElementException(by.toString());
        }

        return element;
    }

    private WebElement tryWaitForElement(By by, int seconds) {
        WebElement element = null;
        try {
            element = fluentWait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Try find element " + by.toString() + ", not found");
        }

        return element;
    }
    private void handleNewsLetterOverlay() {
        tryWaitForElement(By.className("close"), 30);
        List<WebElement> closeButtons = driver.findElements(By.className("close"));

        for (WebElement element : closeButtons) {
            String attribute = element.getAttribute("data-webdriver-automation-id");
            if (attribute.equalsIgnoreCase("newsletter-overlay-close-link")) {
                System.out.println("DEBUG: About to dismiss news letter overlay");
                element.click();
                break;
            }
        }
    }

}