package week4.Day1Assignment3;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 */
public class framesCherCher {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {

		framesCherCher objSrvNowFrms = new framesCherCher();

		// Step 0: Configure the Driver
		ChromeDriver mainDriver = objSrvNowFrms.ConfigureDriver();
		Thread.sleep(2000);
		
		/*
		 * There are 3 frames in this page:
		 * 	Frame 1 - Contains a body, a text box, and another frame (frame 3)
		 * 	Frame 2 - Contains a drop down box for animals
		 *  Frame 3 - Resides within Frame 1, and contains a check box ("Inner frame check box")
		 */

		// Step 1a: Get the Section Title
		String strSecTitle = mainDriver.findElement(By.xpath("//h1[@class='breadcrumb-item']")).getText();
		System.out.println("The Section Title is : " + strSecTitle);
		
		// Step 1b: Get the Page Title
		String strPgTitle = mainDriver.findElement(By.xpath("//label[contains(text(),'Topic : ')]")).getText();
		System.out.println("The Section Title is : " + strPgTitle);
		
		
		// Step 2a: Switch into the frame # 1
		WebElement frame1 = mainDriver.findElement(By.xpath("//iframe[@id='frame1']"));
		mainDriver.switchTo().frame(frame1);
		
		// Step 2b: Enter text into the text box ("Topic")
		WebElement elemTopicText = mainDriver.findElement(By.xpath("//input[@type='text']"));
		elemTopicText.sendKeys("Selenium Training on Frames");
		
		// Step 3a: Step into the inner frame (frame 3)
		WebElement frame3 = mainDriver.findElement(By.xpath("//iframe[@id='frame3']"));
		mainDriver.switchTo().frame(frame3);
		
		// Step 3b: Check the checkbox, if it is not checked already
		WebElement elemInnFrmChkBx = mainDriver.findElement(By.xpath("//input[@id='a' and @type='checkbox']"));
		boolean chkBxSelected = elemInnFrmChkBx.isSelected();
		System.out.println("Checkbox selection status is : " + chkBxSelected);
		
		if (!chkBxSelected) {
			System.out.println("The checkbox is not already selected; checking it now!!!");
			elemInnFrmChkBx.click();
		} else {
			System.out.println("The checkbox is already selected; No further action needed!!!");
		}
		
		// Step 4a: Step into the Main page (default content)
		mainDriver.switchTo().defaultContent();
		
		// Step 4b: Switch into the frame # 2
		WebElement frame2 = mainDriver.findElement(By.xpath("//iframe[@id='frame2']"));
		mainDriver.switchTo().frame(frame2);
		
		// Step 4c: Select the 2nd item, and check if it is Baby Cat
		WebElement elemAnimDrpDn = mainDriver.findElement(By.xpath("//select[@id='animals']"));
		
		Select drpDnAnimals = new Select(elemAnimDrpDn);
		drpDnAnimals.selectByIndex(1);
		String strAnimSelected = elemAnimDrpDn.getAttribute("value");
		System.out.println("The selected drop down value is : " + strAnimSelected);
		
		if(strAnimSelected.equals("babycat")) {
			System.out.println("BABY CAT is correctly selected");
		} else {
			System.out.println("Incorrect selection made in the drop down box!!!!");
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
		driver.get("https://chercher.tech/practice/frames-example-selenium-webdriver");
		// Step 0-04: Maximize the window
		driver.manage().window().maximize();
		// Step 0-05: waits for 10 secs if the element is not in the DOM
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

}
