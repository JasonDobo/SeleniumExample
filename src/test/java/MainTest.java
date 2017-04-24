import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


/**
 * Created by jason.dobo on 24/04/2017.
 */
public class MainTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOne() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/jasondobo/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com");
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

}