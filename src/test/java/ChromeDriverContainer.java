import interfaces.IDriverContainer;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by jason.dobo on 10/05/2017.
 */
public class ChromeDriverContainer implements IDriverContainer {

    ChromeDriver driver = null;

    public ChromeDriverContainer() {
        driver = getDriver();
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
