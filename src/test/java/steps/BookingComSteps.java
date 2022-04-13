package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.concurrent.TimeUnit;

public class BookingComSteps {

    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @After
    public void shutDown() {
        driver.quit();
    }

    @Given("User is on start page")
    public void userIsOnStartPage() {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
    }

    @When("User input name of hotel {string} in search field")
    public void userInputNameOfHotelInSearchField(String hotelName) {
        driver.findElement(By.xpath("//input[@name = 'ss']")).sendKeys(hotelName);
    }

    @And("Click Search button")
    public void clickSearchButton() {
        driver.findElement(By.xpath("//span[@class = 'e57ffa4eb5']")).click();
    }

    @Then("Check that right hotel visible on page and check that hotel rating is {string}")
    public void checkThatRightHotelVisibleOnPageAndCheckThatHotelRatingIs(String rating) {
        boolean isHotelVisible = driver.findElement(By.xpath("//div[contains(text(), 'Bed&Bike Tremola San Gottardo')]")).isDisplayed();
        String ratingText = driver.findElement(By.xpath("//div[@aria-label='Scored 9.2 ']")).getText();
        Assert.assertTrue(isHotelVisible, "Hotel name is not visible");
        Assert.assertEquals(ratingText, rating);
    }
}
