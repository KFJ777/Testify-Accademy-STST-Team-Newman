import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import java.util.Objects;

@Epic("Konga Automation Test")
@Feature("E2E Shopping Flow")
@Owner("Fidelis Bishop")  // 👈 shows tester name in Allure
public class FinalKongaTestAutomationProject {

    WebDriver driver;

    @BeforeTest
    @Description("Setup browser before starting test")
    @Severity(SeverityLevel.BLOCKER)
    public void setUp() {
        driver = new FirefoxDriver();   // ✅ fixed (no "WebDriver" redeclaration)
        driver.manage().window().maximize();
    }

    @Test(description = "Full end-to-end shopping journey on Konga with invalid card payment")
    @Story("User signs in, shops, adds to cart, and attempts payment")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Konga Website", url = "https://www.konga.com")  // 👈 clickable link in report
    public void kongaShoppingTest() {
        stepOpenKonga();
        stepLogin();
        stepShopComputer();
        stepCheckout();
        stepPayment();

    }

    @Step("Open Konga homepage")
    public void stepOpenKonga() {

        //1. Visit the URL Konga
        driver.get("https://www.konga.com/");
        sleep(5000);
        attachText("Final STST Konga Automation Project", driver.getTitle());  // ✅ fixed attachment
    }

    @Step("Login with valid credentials")
    public void stepLogin() {

        //2. Sign in to Konga Successfully
        //2.1. Click on Login
        driver.findElement(By.xpath("//*[@id=\"nav-bar-fix\"]/div[1]/div/div[4]/a")).click();
        sleep(5000);
        //2.2. Enter your Username
        driver.findElement(By.id("username")).sendKeys("fidelisbishop@gmail.com");
        sleep(5000);
        //2.3. Enter your Pasword
        driver.findElement(By.id("password")).sendKeys("Carbon1680");
        sleep(5000);
        //2.4. Click on the Login Button
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[4]/section/section/div/aside/div[2]/div/form/div[3]/button")).click();
        sleep(5000);
    }

    @Step("Navigate to Computers and Accessories, select MacBook")
    public void stepShopComputer() {

        //3. From theh Categories,
        // 3.1. Click on the "Computer and Accessories" - Major (Along the Header)
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/nav/div[3]/div[2]/div/a[2]")).click();
        sleep(5000);
        //3.2. Click on another "Computer and Accessories" - Minor (Along the Side-bar Categories)
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[6]/a")).click();
        sleep(10000);

        //4. Click on the Laptop Subcategory
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[3]/a")).click();
        sleep(5000);

        //5. Click on the Apple MacBooks
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[3]/section/main/section/section/section/section/div/section/div[2]/div[2]/ul/li[1]/a/ul/li[1]/a")).click();
        sleep(5000);

        //6. Add an Item to cart
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/section/section/section/section/section/section/ul/li[2]/article/div[2]/form/div[2]/button")).click();
        sleep(5000);
    }

    @Step("Checkout the cart")
    public void stepCheckout() {
        //6.1. Open Cart
        driver.findElement(By.xpath("//*[@id=\"nav-bar-fix\"]/div[1]/div/a[3]/span[1]")).click();
        sleep(5000);
        //6.2. Click on "Continue to Checkout" button
        driver.findElement(By.xpath("//*[@id=\"app-content-wrapper\"]/div[3]/section/section/div/aside/div[3]/div/div[2]/button")).click();
        sleep(8000);
    }

    @Step("Proceed to payment with invalid card details")
    public void stepPayment() {

        //7. Change Address
        //7.1 Choose the appropriate address
        //7.1 Click on the "Pay Now" button
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/section[2]/div/div[2]/div[1]/div[1]/span/input")).click();
        sleep(5000);

        //8. Click on the "Continue to Payment" button
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/section[2]/div/div[2]/div[3]/div[2]/div/button")).click();
        sleep(10000);
        //8.1. Switch to iFrame
        driver.switchTo().frame("kpg-frame-component");
        sleep(10000);

        //9. Select a Card Payment Method
        driver.findElement(By.xpath("/html/body/section/section/section/div[2]/div[3]/div[1]/div[2]/div/div[3]/button")).click();
        sleep(5000);

        //10. Input invalid  card details
        //10.1. Input Invalid Card number
        driver.findElement(By.id("card-number")).sendKeys("1234432167899876");
        sleep(5000);
        //10.2. Input Invalid Card expiry date
        driver.findElement(By.id("expiry")).sendKeys("0507");
        sleep(1000);
        //10.3. Input Invalid CVV number
        driver.findElement(By.id("cvv")).sendKeys("16");
        sleep(5000);
        //10.4. Click on the "Pay Now" button to simulate payment
        driver.findElement(By.id("validateCardForm")).click();
        sleep(5000);

        //11.Print out Error Message (Allure)
        attachText("Error message (if any)", "Invalid card details entered");
        Allure.step("Capture error message for report");
        //11.1. Capture card number error
        String cardError = driver.findElement(By.id("card-number_unhappy")).getText();
        Allure.step("Card error captured: " + cardError);
        //11.2. Capture expiry error
        String expiryError = driver.findElement(By.id("expiry_unhappy")).getText();
        Allure.step("Expiry error captured: " + expiryError);
        //11.3. Capture CVV error
        String cvvError = driver.findElement(By.id("cvv_unhappy")).getText();
        Allure.step("CVV error captured: " + cvvError);

        Allure.addAttachment("card-number_unhappy", cardError);
        Allure.addAttachment("expiry_unhappy", expiryError);
        Allure.addAttachment("cvv_unhappy", cvvError);

        //11.4. Console Print out Message
        System.out.println("Invalid card number" + "Invalid expiry date" + "Invalid cvv");

        //12. Close the iFrame that displays the input card Modal
        driver.findElement(By.xpath("//html/body/section/section/section/div[2]/div[1]/aside")).click();
        sleep(5000);

    }

    @SuppressWarnings("unused")
    @Attachment(value = "{0}", type = "text/plain")
    public void attachText(String name, String content) {
    }


    @AfterTest
    @Description("Close browser after test execution")
    @Severity(SeverityLevel.MINOR)
    public void tearDown() {

        //13. Quit the browser
        if (Objects.nonNull(driver)) {
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
