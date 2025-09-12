import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Konga Automation Test")
@Feature("E2E Shopping Flow")
@Owner("Bishop IME (KFJ777)")
public class AutomationTestForkonga {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //1. Visit the URL Konga
        driver.get("https://www.konga.com");
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void LoginTest() {
        //2. Sign in to Konga Successfully
        Allure.step("Click login link");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Login')]"))).click();

        Allure.step("Enter username");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys("fidelisbishop@gmail.com");

        Allure.step("Enter password");
        driver.findElement(By.id("password")).sendKeys("Carbon1680");

        Allure.step("Click Login button");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
    }

    @Test(priority = 2)
    public void StepShopComputer() {
        //3. From the Categories
        Allure.step("Click Computer & Accessories (header)");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Computers and Accessories')]"))).click();

        Allure.step("Click Computer Accessories (sidebar)");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Computer Accessories')]"))).click();

        Allure.step("Click Laptop Subcategory");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Laptops')]"))).click();

        Allure.step("Click Apple MacBooks");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Apple MacBooks')]"))).click();

        Allure.step("Add item to cart");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Add to cart') or contains(text(),'BUY NOW')]"))).click();
    }

    @Test(priority = 3)
    public void StepCheckOut() {
        //6.1. Open Cart
        Allure.step("Open Cart");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='nav-bar-fix']//a[contains(@href,'cart')]"))).click();

        //6.2. Click on "Continue to Checkout" button
        Allure.step("Click Continue to Checkout");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Checkout') or contains(text(),'Continue')]"))).click();
    }

    @Test(priority = 4)
    public void StepPayment() {
        //7. Select Delivery Address
        Allure.step("Select Delivery Address");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mainContent']//input[@type='radio']"))).click();

        Allure.step("Click Continue to Payment");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Continue to Payment')]"))).click();

        //8. Switch to iFrame
        Allure.step("Switch to Payment iFrame");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("kpg-frame-component"));

        //9. Select Card Payment Method
        Allure.step("Select Card Payment");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Card')]"))).click();

        //10. Input invalid  card details
        Allure.step("Enter invalid card number");
        driver.findElement(By.id("card-number")).sendKeys("1234432167899876");

        Allure.step("Enter invalid expiry date");
        driver.findElement(By.id("expiry")).sendKeys("0507");

        Allure.step("Enter invalid CVV");
        driver.findElement(By.id("cvv")).sendKeys("16");

        Allure.step("Click Pay Now");
        driver.findElement(By.id("validateCardForm")).click();

        Allure.step("Click Pay Now");
        driver.findElement(By.id("validateCardForm")).click();
    }

    @Test(priority = 5)
    public void PrintOutError() {
        //11. Print out Error Message (Allure)
        Allure.step("Capture and log card errors");
        attachText("Error message (if any)", "Invalid card details entered");

        String cardError = getTextSafe(By.id("card-number_unhappy"));
        Allure.step("Invalid card number: " + cardError);

        String expiryError = getTextSafe(By.id("expiry_unhappy"));
        Allure.step("Invalis expiry date: " + expiryError);

        String cvvError = getTextSafe(By.id("cvv_unhappy"));
        Allure.step("Invalid cvv: " + cvvError);

        // Attach errors into Allure report
        attachText("Card Error", cardError);
        attachText("Expiry Error", expiryError);
        attachText("CVV Error", cvvError);

        System.out.println("Invalid card number | Invalid expiry date | Invalid cvv");

        Allure.step("Close Payment iFrame modal");
        driver.switchTo().defaultContent();
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String attachText(String name, String content) {
        return content;
    }

    private String getTextSafe(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            return "No error message displayed";
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
