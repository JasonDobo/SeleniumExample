import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTest {

    WebDriver driver;
    private int DEFAULT_TIMEOUT = 10;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/jasondobo/Downloads/chromedriver");
        driver = new ChromeDriver();

        driver.get("https://www.moo.com/uk/");
        handleNewsLetterOverlay();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    // 1. When a customer searches for a valid product on the website, they should see a view of
    // products matching that search term - example: business cards
    @Test
    public void testSuccessfulSearchForProducts() {
        enterSearchString("business cards");
        pressSearchButton();

        waitForVisibilityOfElement(By.className("gsc-results"));
        waitForInvisibilityOfElement(By.className("gs-no-results-result"));
        List<WebElement> results = driver.findElements(By.className("gsc-result"));
        Assert.assertTrue(results.size() > 0);
    }

    // 2. When a customer searches for an invalid product on the website, they should receive a
    // message telling them - example: sdjfnjsdfj
    @Test
    public void testUnSuccessfulSearchForProducts() {
        enterSearchString("sdjfnjsdfj");
        pressSearchButton();

        waitForVisibilityOfElement(By.className("gs-no-results-result"));
        waitForInvisibilityOfElement(By.className("gsc-results"));
    }

    // 3. When a customers logs in with valid credentials on the web site, then the your account screen is displayed
    // In practise you should be using a test environment and not create test accounts on live
    @Test
    public void testSignInWithValidCredentials() {
        pressAccountIcon();
        WebElement label = waitForVisibilityOfElement(By.id("fb-label"));
        Assert.assertTrue(label.getText().equalsIgnoreCase("Sign in with"));

        enterUserSignInCredentials("jason.dobo@gmail.com", "qwerty123");
        pressSignInButton();

        waitForVisibilityOfElement(By.linkText("Your Account"));
    }

    // 4. When a customers is on the sign page, then they can choose to create a account and enter details
    // In practise you should be using a test environment and not create test accounts on live
    @Test
    public void testCreateAnAccount() throws InterruptedException {
        pressAccountIcon();
        WebElement label = waitForVisibilityOfElement(By.id("fb-label"));
        Assert.assertTrue(label.getText().equalsIgnoreCase("Sign in with"));

        findSignupOption();

        enterTextInElementFoundBy(By.id("txtFirstName"), "Jason");
        enterTextInElementFoundBy(By.id("txtLastName"), "Dobo");
        enterTextInElementFoundBy(By.id("txtEmailSignUp"), "jason.dobo@gmail.com");

        final String password = "qwerty123";
        enterTextInElementFoundBy(By.id("txtPasswordSignUp"), password);
        enterTextInElementFoundBy(By.id("txtPassword2SignUp"), password);

        selectNewSpinnerOption("fancy-select-id-3");
        selectNewSpinnerOption("fancy-select-id-11");
        selectNewSpinnerOption("fancy-select-id-5");

        WebElement btnSignup = waitToBeClickableForElement(By.id("btnSignup"));
//        btnSignup.click(); As I do not want to create multiple accounts on a live system I've disabled this part of the test
    }

    // Action helpers
    private void handleNewsLetterOverlay() {
        tryWaitForElement(ExpectedConditions.elementToBeClickable(By.className("close")), 30);
        List<WebElement> closeButtons = driver.findElements(By.className("close"));

        for (WebElement element : closeButtons) {
            String attribute = element.getAttribute("data-webdriver-automation-id");
            if (attribute.equalsIgnoreCase("newsletter-overlay-close-link")) {
                System.out.println("DEBUG: About to dismiss news letter overlay");
                element.click();

                Boolean close = waitForInvisibilityOfElement(By.className("close"));
                Assert.assertTrue(close);
                break;
            }
        }
    }

    private void findSignupOption() {
        List<WebElement> radioButtons = driver.findElements(By.className("fancy-radio"));
        for (WebElement element : radioButtons) {
            String attribute = element.getAttribute("data-webdriver-automation-id");
            if (attribute != null && attribute.equalsIgnoreCase("signup-option")) {
                element.click();
                waitForVisibilityOfElement(By.id("lblFirstName"));
                break;
            }
        }
    }

    private void selectNewSpinnerOption(String industryId) throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement element = waitForVisibilityOfElement(By.id(industryId));
        actions.moveToElement(element).click().perform();

        String style = element.findElement(By.className("flyout-standard")).getAttribute("style");
        Assert.assertTrue(style.contains("visible"));

        List<WebElement> options = element.findElements(By.className("content-text"));
        Assert.assertTrue(options.size() >= 3);
        WebElement toBeSelected = options.get(3);

        fluentWait(5).until(ExpectedConditions.elementToBeClickable(toBeSelected));
        actions.moveToElement(toBeSelected).click().perform();
        style = element.findElement(By.className("flyout-standard")).getAttribute("style");
        Assert.assertTrue(style.contains("hidden"));
        Thread.sleep(1000);
    }

    private void enterSearchString(String criteria) {
        WebElement searchBar = waitToBeClickableForElement(By.id("query"));

        searchBar.click();
        searchBar.sendKeys(criteria);
    }

    private void enterUserSignInCredentials(String email, String password) {
        enterTextInElementFoundBy(By.id("txtEmailSignIn"), email);
        enterTextInElementFoundBy(By.id("txtPasswordSignIn"), password);
    }

    private void enterTextInElementFoundBy(By by, String text) {
        WebElement element = waitForVisibilityOfElement(by);
        element.sendKeys(text);
    }

    private void pressSignInButton() {
        WebElement signInButton = waitToBeClickableForElement(By.id("btnLogin"));
        signInButton.click();
    }

    private void pressSearchButton() {
        WebElement searchButton = waitForVisibilityOfElement(By.className("search__btn"));
        searchButton.click();
    }

    private void pressAccountIcon() {
        WebElement account = waitToBeClickableForElement(By.className("js-nav__link--account"));
        account.click();
    }

    // Element finders and checkers
    private Wait<WebDriver> fluentWait(long timeout) {
        return new FluentWait<>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    private WebElement waitForVisibilityOfElement(By by) {
        WebElement element = tryWaitForElement(ExpectedConditions.visibilityOfElementLocated(by), DEFAULT_TIMEOUT);

        if (element == null) {
            System.out.println("Waited for visibility of element by " + by.toString() + ", not found");
            throw new NoSuchElementException(by.toString());
        } else {
            return element;
        }
    }

    private WebElement waitToBeClickableForElement(By by) {
        WebElement element = tryWaitForElement(ExpectedConditions.elementToBeClickable(by), DEFAULT_TIMEOUT);

        if (element == null) {
            System.out.println("Waited for clickable of element by " + by.toString() + ", not found");
            throw new NoSuchElementException(by.toString());
        } else {
            return element;
        }
    }

    private WebElement tryWaitForElement(ExpectedCondition<WebElement> condition, int seconds) {
        WebElement element = null;

        try {
            element = fluentWait(seconds).until(condition);
        } catch (TimeoutException e) {
            System.out.println("Try find element " + condition.toString() + ", not found");
        }

        return element;
    }

    private boolean waitForInvisibilityOfElement(By by) {
        int seconds = DEFAULT_TIMEOUT;
        boolean result = false;

        try {
            result = fluentWait(seconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Try find result " + by.toString() + ", not found");
        }

        return result;
    }
}