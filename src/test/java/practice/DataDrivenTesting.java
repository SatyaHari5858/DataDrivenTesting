package practice;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.XLUtility;

public class DataDrivenTesting {
	public WebDriver driver;

	@BeforeClass
	public void StartDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://www.saucedemo.com/");
	}

	@Test(dataProvider = "logindata")
	public void LoginTest(String username, String password, String result) throws InterruptedException {
		WebElement email = driver.findElement(By.xpath("//div[@class='login-box']/descendant::input[@id='user-name']"));
		email.clear();
		email.sendKeys(username);

		WebElement pass = driver.findElement(By.xpath("//div[@class='form_group']/following::input[@id='password']"));
		pass.clear();
		pass.sendKeys(password);

		WebElement loginbtn = driver
				.findElement(By.xpath("//div[@class='form_group']/following::input[@id='login-button']"));

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", loginbtn);

		String expected = null;
		String actual = null;

		try {
			expected = "Products";
			actual = driver.findElement(By.xpath("//span[text()='Products']")).getText();
			System.out.println("Title is " + actual);
		} catch (Exception e) {
			e.getMessage();
		}

		try {
			driver.findElement(
					By.xpath("//div[@id='menu_button_container']/descendant::button[@id='react-burger-menu-btn']"))
					.click();
			driver.findElement(By.xpath("//div[@class='bm-menu-wrap']/descendant::a[@id='logout_sidebar_link']"))
					.click();
		} catch (Exception e) {
			e.getMessage();
		}

		if (result.equalsIgnoreCase("Success")) {
			if (expected.equalsIgnoreCase(actual)) {

				Assert.assertTrue(true);
				Thread.sleep(3000);

			} else {
				Assert.assertTrue(false);
			}
		} else if (result.equalsIgnoreCase("Fail")) {
			if (expected.equalsIgnoreCase(actual)) {
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}
		}

	}

	@AfterTest
	public void CloseDriver() {
		driver.close();

	}

	@DataProvider(name = "logindata")
	public String[][] inputdata() throws IOException {

		/*
		 * Hard Coded
		 */
//		String[][] logindata = {
//
//				{ "standard_user", "secret_sauce", "success" }, { "satya1@gmail.com", "Satya@123", "Fail" } };

		/*
		 * Soft Coded
		 */
		String path = "src\\test\\java\\utils\\data.xlsx";

		XLUtility xlutil = new XLUtility(path);
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		String[][] logindata = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) {

			for (int j = 0; j < totalcols; j++) {
				logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}

		return logindata;

	}
}
