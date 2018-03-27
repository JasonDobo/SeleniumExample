import interfaces.IDriverContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverContainer implements IDriverContainer {

    private static ChromeDriverContainer instance = null;
    private static ChromeDriver driver = null;

    public static WebDriver webDriver = null;

    public ChromeDriverContainer() {
        driver = getDriver();
    }

    static ChromeDriverContainer getInstance() {
        if (instance == null) {
            instance = new ChromeDriverContainer();
        }

        return instance;
    }

    public ChromeDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "/Users/jason.dobo/Downloads/chromedriver");
            driver = new ChromeDriver();
        }

        return driver;
    }

    public void deleteDriver() {
        driver = null;
    }
}
