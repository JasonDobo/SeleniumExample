package interfaces;

import org.openqa.selenium.WebDriver;

public interface IDriverContainer {

    WebDriver getDriver();

    void deleteDriver();

}
