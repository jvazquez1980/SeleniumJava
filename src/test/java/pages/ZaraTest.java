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
import java.util.Random;

public class ZaraTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testZaraPage() {
        // Navega a zara.com
        driver.get("https://www.zara.com/es/");

        // Aceptar cookies si está presente
        List<WebElement> acceptCookiesButton = driver.findElements(By.id("onetrust-accept-btn-handler"));
        if (!acceptCookiesButton.isEmpty()) {
            acceptCookiesButton.get(0).click();
        }

        // Hacer clic en el menú
        clickElement(By.cssSelector("[data-qa-id='layout-header-toggle-menu']"));

        // Hacer clic en el carrito
        clickElement(By.cssSelector("[data-qa-id='layout-header-go-to-cart']"));

        // Verificar que todas las imágenes tienen el atributo 'src'
        List<WebElement> mediaImages = driver.findElements(By.cssSelector("[data-qa-qualifier='media-image']"));
        for (WebElement image : mediaImages) {
            Assert.assertTrue(image.getAttribute("src") != null, "La imagen no tiene el atributo 'src'");
        }

        // Hacer clic en un producto aleatorio
        List<WebElement> products = driver.findElements(By.cssSelector("[data-qa-action='product-click']"));
        if (!products.isEmpty()) {
            WebElement randomProduct = products.get(new Random().nextInt(products.size()));
            randomProduct.click();
        } else {
            Assert.fail("No se encontraron productos en la lista");
        }

        // Hacer clic en "Añadir"
        clickElement(By.xpath("//*[contains(text(), 'Añadir')]"));

        // Seleccionar la primera talla disponible
        clickElement(By.cssSelector("[data-qa-action='size-in-stock']"));

        // Esperar a que el loader desaparezca
        waitForElementToDisappear(By.cssSelector(".loader--basic"));

        // Verificar que el carrito contiene 1 artículo
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa-id='layout-header-go-to-cart-items-count']")));
        Assert.assertTrue(cartCount.getText().trim().contains("1"), "El carrito no contiene 1 artículo");

        // Continuar con la compra
        clickElement(By.cssSelector("[data-qa-id='shop-continue']"));

        // Verificar que el botón de iniciar sesión es visible
        WebElement initSession = driver.findElement(By.cssSelector("[data-qa-id='oauth-logon-button']"));
        Assert.assertTrue(initSession.isDisplayed(), "El botón de iniciar sesión es visible");
    }

    private void clickElement(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
            element.click();
        } catch (Exception e) {
            Assert.fail("No se pudo hacer clic en el elemento: " + by.toString());
        }
    }

    private void waitForElementToDisappear(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @AfterClass
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}