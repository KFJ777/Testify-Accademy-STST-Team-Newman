import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.Objects;

@Epic("Konga Automation Test2")
@Feature("E2E Shopping Flow")
@Owner("Fidelis Bishop")  // 👈 shows tester name in Allure
public class FinalSTSTKongaAutomationProject_13092024 {

    WebDriver driver;

    @BeforeTest
    @Description("Setup browser before starting test")
    @Severity(SeverityLevel.BLOCKER)
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test(description = "Full end-to-end shopping journey on Konga with invalid card payment")
    @Story("User signs in, shops, adds to cart, and attempts payment")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Konga Website", url = "https://www.konga.com") // 👈 clickable link in report
    public void kongaShoppingTest() {
        stepOpenKonga();
        stepLogin();
        stepShopComputer();
        stepCheckout();
        stepPayment();
    }

    @Step("Open Konga homepage")
    public void stepOpenKonga() {
        driver.get("https://www.konga.com/");
        sleep(5000);
        Allure.addAttachment("Final STST Konga Automation Project", Objects.requireNonNull(driver.getTitle()));
    }

    @Step("Login with valid credentials")
    public void stepLogin() {
        driver.findElement(By.xpath("//*[@id=\"nav-bar-fix\"]/div[1]/div/div[4]/a")).click();
        sleep(5000);
        driver.findElement(By.id("username")).sendKeys("fidelisbishop@gmail.com");
        sleep(5000);
        driver.findElement(By.id("password")).sendKeys("Carbon1680");
        sleep(5000);
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[4]/section/section/div/aside/div[2]/div/form/div[3]/button")).click();
        sleep(5000);
    }

    @Step("Navigate to Computers and Accessories, select MacBook")
    public void stepShopComputer() {
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/nav/div[3]/div[2]/div/a[2]")).click();
        sleep(5000);
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[6]/a")).click();
        sleep(10000);
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[3]/a")).click();
        sleep(5000);
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[1]/a/ul/li[1]/a")).click();
        sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/section/section/section/section/section/section/ul/li[2]/article/div[2]/form/div[2]/button")).click();
        sleep(5000);
    }

    @Step("Checkout the cart")
    public void stepCheckout() {
        driver.findElement(By.xpath("//*[@id=\"nav-bar-fix\"]/div[1]/div/a[3]/span[1]")).click();
        sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"app-content-wrapper\"]/div[3]/section/section/div/aside/div[3]/div/div[2]/button")).click();
        sleep(5000);
    }

    @Step("Proceed to payment with invalid card details")
    public void stepPayment() {
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/section[2]/div/div[2]/div[1]/div[1]/span/input")).click();
        sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/section[2]/div/div[2]/div[3]/div[2]/div/button")).click();
        sleep(10000);

        driver.switchTo().frame("kpg-frame-component");
        sleep(10000);

        driver.findElement(By.xpath("/html/body/section/section/section/div[2]/div[3]/div[1]/div[2]/div/div[3]/button")).click();
        sleep(5000);

        driver.findElement(By.id("card-number")).sendKeys("1234432167899876");
        sleep(5000);
        driver.findElement(By.id("expiry")).sendKeys("0507");
        sleep(1000);
        driver.findElement(By.id("cvv")).sendKeys("16");
        sleep(5000);

        driver.findElement(By.id("validateCardForm")).click();
        sleep(5000);

        attachText("Error message (if any)", "Invalid card details entered");

        driver.findElement(By.xpath("//html/body/section/section/section/div[2]/div[1]/aside")).click();
        sleep(5000);

        System.out.println("Invalid card number | Invalid expiry date | Invalid cvv");

        Allure.step("Close Payment iFrame modal");
        driver.switchTo().defaultContent();
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String attachText(String name, String content) {
        return content;
    }

    @AfterTest
    @Description("Close browser after test execution")
    @Severity(SeverityLevel.MINOR)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
