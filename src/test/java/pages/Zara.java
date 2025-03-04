package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
//This is a proyect with Selenium Gradle and TestNG 
public class Zara {
    // Implicit wait
    protected WebDriver driver;
    WebDriverWait wait;

    // Method to find and return a WebElement on the page using a locator, waiting for it to be present in the DOM
    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Method to click on an element using a locator
    public void clickElement(By locator) {
        findElement(locator).click();
    }

    // Method to navigate to a URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    // Selectors idealy should be in a separate file as a support file
    private By acceptCookiesSelector = By.id("onetrust-accept-btn-handler");
    private By search = By.cssSelector("[data-qa-id='header-search-text-link']");
    private By cart = By.cssSelector("[data-qa-id='layout-header-go-to-cart']");
    private By products = By.cssSelector("[data-qa-action='product-click']");
    private By size = By.cssSelector("[data-qa-action='size-in-stock']");
    private By continueButton = By.cssSelector("[data-qa-id='shop-continue']");
    private By addButton = By.xpath("//*[text()='Añadir']");
    private By logonButton = By.cssSelector("[data-qa-id='oauth-logon-button']");


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testZaraLogo() {
        // Navega a zara.com
        navigateTo("https://www.zara.com/es/");
        // Aceptar cookies si está presente
        clickElement(acceptCookiesSelector);
        // Encuentra el logo usando el atributo data-qa-action
        clickElement(cart);
        // Hacer clic en el botón "Search"
        clickElement(search);

        // Verificar que el elemento media tiene el atributo src
        List<WebElement> mediaElements = driver.findElements(products);
        for (WebElement mediaElement : mediaElements) {
            String src = mediaElement.getDomAttribute("src");
            Assert.assertNotNull(src, "El atributo 'src' es nulo.");
            Assert.assertFalse(src.isEmpty(), "El atributo 'src' está vacío.");
        }

        clickElement(products);
        clickElement(addButton);
        clickElement(size);
        clickElement(cart);
        clickElement(continueButton);
         WebElement logonBtn = wait.until(ExpectedConditions.presenceOfElementLocated(logonButton));
        Assert.assertTrue(logonBtn.isDisplayed(), "El botón de iniciar sesión está presente.");
    }

    @AfterClass
    public void tearDown() {
            driver.quit();
    }
}