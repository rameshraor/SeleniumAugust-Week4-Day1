/**
 * 
 */
package week4.Day1Assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author bindh
 */
public class ServiceNowFrames {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {

		ServiceNowFrames objSrvNowFrms = new ServiceNowFrames();

		// Step 0: Configure the Driver
		ChromeDriver mainDriver = objSrvNowFrms.ConfigureDriver();
		Thread.sleep(2000);

		// Step 1a: Switch into the frame
		WebElement frame1 = mainDriver.findElement(By.xpath("//iframe[@id=\"gsft_main\"]"));
		mainDriver.switchTo().frame(frame1);

		// Step 1b: Enter username as admin
		mainDriver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");

		// Step 1c: Enter password as w6hnF2FRhwLC
		mainDriver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("w6hnF2FRhwLC");

		// Step 1d: Click Login
		mainDriver.findElement(By.xpath("//*[@id=\"sysverb_login\"]")).click();
		Thread.sleep(1000);

		// Step 2a: The below statement will take the control back to the main page -
		// even though we have moved to another page
		mainDriver.switchTo().defaultContent();

		// Step 2b: search for "incident" in the filter search box
		WebElement elemFilter = mainDriver.findElement(By.xpath("//input[@id='filter']"));
		elemFilter.clear();
		elemFilter.sendKeys("incident", Keys.ENTER);
		Thread.sleep(1000);

		// Step 2c: Click "All" under Incidents
		WebElement elemIncAll = mainDriver
				.findElement(By.xpath("//ul[@aria-label='Modules for Application: Incident']/li[6]"));
		elemIncAll.click();
		Thread.sleep(1000);

		// Step 2d: Switch to the right-side frame (in which the "New" button is placed
		WebElement frame2 = mainDriver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		mainDriver.switchTo().frame(frame2);

		// Step 2c: Click New button
		mainDriver.findElement(By.xpath("//button[@id='sysverb_new']")).click();
		Thread.sleep(1000);

		// Step 3a: Click on the lens icon against "caller" - after switching to default
		// frame
		// mainDriver.switchTo().defaultContent();
		// The form name remains the same, so I am not switching to the frame
		mainDriver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']")).click();

		// Step 3b - Handle windows (because the earlier action opens a pop-up window)
		Set<String> windowHandlesSet1 = mainDriver.getWindowHandles();
		List<String> windowHandlesList1 = new ArrayList<String>(windowHandlesSet1);
		mainDriver.switchTo().window(windowHandlesList1.get(1));

		// Step 3c - Enter "Ram" in the search box, and hit Enter
		mainDriver.findElement(By.xpath("(//label[@class='sr-only'])[2]/following-sibling::input")).sendKeys("Ram",
				Keys.ENTER);
		Thread.sleep(1000);

		// Step 3d - Select the first entry in the table (in the pop-up window), by
		// clicking the same
		mainDriver.findElement(By.xpath("//tbody[@class='list2_body']/tr[2]/td[3]/a")).click();

		// Step 3e - Switch to the parent window, and switch to the right-side frame
		mainDriver.switchTo().window(windowHandlesList1.get(0));
		mainDriver.switchTo().frame("gsft_main");

		// Step 3f - Enter the short description
		mainDriver.findElement(By.xpath("//input[@id='incident.short_description']"))
				.sendKeys("This incident is opened to track the call drops in the customer calls");

		// Step 3g: Read the incident number and save it as a variable
		String incNumber = mainDriver.findElement(By.xpath("//input[@name='incident.number']")).getAttribute("value");
		System.out.println("The new incident Number that has been opened is : " + incNumber);
		Thread.sleep(1000);

		// Step 3h: click submit button at the top
		mainDriver.findElement(By.xpath("//button[@id='sysverb_insert']")).click();

		// Step 4a - Search the same incident number in the next search screen as below.
		mainDriver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(incNumber, Keys.ENTER);
		Thread.sleep(1000);
		
		//Step 5: Verify the incident is created successful and take snapshot of the created incident.
		if (mainDriver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]")).isDisplayed()) {
			String actIncNbr = mainDriver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]"))
					.getAttribute("aria-label");
			if (actIncNbr.contains(incNumber)) {
				System.out.println("Incident " + incNumber + " has been created successfully");
				File fileSrc = mainDriver.getScreenshotAs(OutputType.FILE);
				File fileDest = new File("./snaps/IncidentNumber.png");
				FileUtils.copyFile(fileSrc, fileDest);
			} else
				System.out.println("Incident is not created successfully. Expected : " + incNumber + " but Actual displayed here : "
						+ actIncNbr);

		} else {
			System.out.println("No results displayed for the incident number.");
		}

	}

	public ChromeDriver ConfigureDriver() {

		// Below are standard steps to be included (except the URL)

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		// Step 0-01: Download and set the path
		WebDriverManager.chromedriver().setup();
		// Step 0-02: Launch the Chrome browser
		ChromeDriver driver = new ChromeDriver(options);
		// Step 0-03: Load the URL
		driver.get("https://dev113545.service-now.com/");
		// Step 0-04: Maximize the window
		driver.manage().window().maximize();
		// Step 0-05: waits for 10 secs if the element is not in the DOM
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

}
