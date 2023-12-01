import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {

    WebDriver driver;
    // This method sets up the WebDriver before each test.

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
    }
    // This method closes the WebDriver after each test.

    @AfterEach
    void tearDown(){
        driver.quit();
    }
    // Helper method to open a specific page in the browser.
    void openPage(){
        driver.get("http://localhost:8081/"); // Replace with your application's URL
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }
    // Helper method for logging into the application.
    void login() {
        openPage(); // Navigate to the page where the form is located
        // Find the user form's input field by its name or ID and input the ID '1'
        WebElement userIdInput = driver.findElement(By.cssSelector("input[name='id']")); // Replace with the actual selector for the user ID input
        userIdInput.sendKeys("3");

        // Find and click the submit button of the form
        WebElement submitButton = driver.findElement(By.cssSelector("form.userForm input[type='submit']")); // Replace with the actual selector for the submit button
        submitButton.click();
        // Add any assertions or additional actions needed after form submission
    }

    // Test to verify that the home page loads correctly.
    @TmsLink("C1")
    @Test
    void testHomePageLoads() {
        openPage();
        WebElement header = driver.findElement(By.className("appHeader"));
        WebElement itemsContainer = driver.findElement(By.className("itemsContainer"));

        assertNotNull(header, "Header should be present");
        assertNotNull(itemsContainer, "Items container should be present");
    }

    // Test to verify that the login functionality works.
    @TmsLink("C2")
    @Test
    void testlogin() {
        openPage(); // Navigate to the page where the form is located

        System.out.println("Filling in the user form...");
        // Find the user form's input field by its name or ID and input the ID '1'
        WebElement userIdInput = driver.findElement(By.cssSelector("input[name='id']")); // Replace with the actual selector for the user ID input
        userIdInput.sendKeys("3");

        System.out.println("Submitting the user form...");
        // Find and click the submit button of the form
        WebElement submitButton = driver.findElement(By.cssSelector("form.userForm input[type='submit']")); // Replace with the actual selector for the submit button
        submitButton.click();

        System.out.println("Form submitted.");
        // Add any assertions or additional actions needed after form submission
    }

    // Test to verify buying an item and checking the stock count.
    @TmsLink("C3")
    @Test
    void testBuyItemAndCheckStock() {
        login();

        // Capture the initial stock number
        WebElement initialStockElement = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div[2]/div[2]/p"));
        int initialStock = extractStockNumber(initialStockElement.getText());
        System.out.println(initialStock);

        // Locate and click the "Buy" button
        WebElement buyButton = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div[2]/div[1]/form[1]/button"));
        buyButton.click();

        // Capture the new stock number
        WebElement newStockElement = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div[2]/div[2]/p"));
        int newStock = extractStockNumber(newStockElement.getText());
        System.out.println("New Stock: " + newStock);
        assertTrue(newStock < initialStock, "Stock should decrease after a purchase");

    }

    // Helper method to extract numeric stock number from text.
    private int extractStockNumber(String stockText) {
        return Integer.parseInt(stockText.replaceAll("[^0-9]", ""));
    }
    // Test to verify submitting an item creation form.
    @TmsLink("C4")
    @Test
    void testItemCreationFormSubmission() {
        login();

        // Fill in the form fields
        driver.findElement(By.id("name")).sendKeys("Joshua");
        driver.findElement(By.id("description")).sendKeys("Description of Test Item");
        driver.findElement(By.id("imageUrl")).sendKeys("http://example.com/testitem.jpg");
        driver.findElement(By.id("locationState")).sendKeys("WA");
        driver.findElement(By.id("price")).sendKeys("100");
        driver.findElement(By.id("stock")).sendKeys("10");
        driver.findElement(By.id("category")).sendKeys("1");


        // Locate and click the "Buy" button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='submit']"));
        submitButton.click();


    }

    // Test to verify adding an item to the cart and then buying it.
    @TmsLink("C5")
    @Test
    void testAddAndBuyFromCart() {
        login();

        // Find and store the initial cart count before adding an item
        WebElement initialCartCountElement = driver.findElement(By.xpath("/html/body/div/div[1]/div[3]/p[1]/span"));
        int initialCartCount = Integer.parseInt(initialCartCountElement.getText().trim());
        System.out.println("Initial Cart Count: " + initialCartCount);


        WebElement addToCartButton = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[1]/form[2]/button"));
        addToCartButton.click();

        //get buy button
        WebElement BuyButton = driver.findElement(By.xpath("/html/body/div/div[1]/div[3]/div/form[1]/button"));
        BuyButton.click();

        // Find and store the new cart count
        WebElement newCartCountElement = driver.findElement(By.xpath("/html/body/div/div[1]/div[3]/p[1]/span"));
        int newCartCount = Integer.parseInt(newCartCountElement.getText().trim());
        System.out.println("New Cart Count: " + newCartCount);

        assertEquals(newCartCount, initialCartCount, "Cart count should remain the same after adding an item");


    }











}
