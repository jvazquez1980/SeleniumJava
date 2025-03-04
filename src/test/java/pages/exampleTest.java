package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class exampleTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testZaraLogo() {
    driver.get("https://wikipedia.es");
			
			WebElement inputDeBusqueda = driver.findElement(By.id("searchInput"));
			
			inputDeBusqueda.click();
            

    }

    @AfterClass
    public void tearDown() {
        // Cierra el navegador
        if (driver != null) {
            driver.quit();
        }
    }
}