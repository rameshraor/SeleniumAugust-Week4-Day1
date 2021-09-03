/**
 * 
 */
package week4.Day1Assignment4;

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
public class FrameScrShotListArray {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {

		FrameScrShotListArray objTstLfFrms = new FrameScrShotListArray();

		// Step 0: Configure the Driver
		ChromeDriver mainDriver = objTstLfFrms.ConfigureDriver();
		Thread.sleep(1000);

		// 1.Take the the screenshot of the click me button of first frame
		// 2.Find the number of frames
		// - find the Elements by tagname - iframe
		// - Store it in a list
		// - Get the size of the list. (This gives the count of the frames visible to
		// the main page)

		// Step 1a: Switch into the frame # 1, which has the clickme button
		WebElement frame1 = mainDriver.findElement(By.xpath("//iframe[@src='default.html']"));
		mainDriver.switchTo().frame(frame1);

		// Step 1b: Locate the clickme button
		WebElement elemBtnClkMe = mainDriver.findElement(By.xpath("//button[@id='Click']"));

		// Step 1c: Take the screenshot of the entire page
		File fileSrc1 = mainDriver.getScreenshotAs(OutputType.FILE);
		File fileDest1 = new File("./snaps/FramesPage.png");
		FileUtils.copyFile(fileSrc1, fileDest1);

		// Step 1d: Take the screenshot of the element alone (clickme button)
		File fileSrc2 = elemBtnClkMe.getScreenshotAs(OutputType.FILE);
		File fileDest2 = new File("./snaps/ClickMeButton.png");
		FileUtils.copyFile(fileSrc2, fileDest2);

		// Step 2a: Switch back to main page
		mainDriver.switchTo().defaultContent();

		// Step 2b: Step into frame # 2
		WebElement frame2 = mainDriver.findElement(By.xpath("//iframe[@src='page.html']"));
		mainDriver.switchTo().frame(frame2);

		// Step 3a: Switch back to main page
		mainDriver.switchTo().defaultContent();

		// Step 3b: Step into frame # 3
		WebElement frame3 = mainDriver.findElement(By.xpath("//iframe[@src='page.html']"));
		mainDriver.switchTo().frame(frame3);

		// Step 4a: Switch back to main page
		mainDriver.switchTo().defaultContent();

		// Find the number of frames
		List<WebElement> lstNbrFrames = mainDriver.findElements(By.tagName("iframe"));
		System.out.println("Total # of frames built into the main page is : " + lstNbrFrames.size());

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
		driver.get("http://leafground.com/pages/frame.html");
		// Step 0-04: Maximize the window
		driver.manage().window().maximize();
		// Step 0-05: waits for 10 secs if the element is not in the DOM
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

}
