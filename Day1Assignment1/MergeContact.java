/**
 * 
 */
package week4.Day1Assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 */
public class MergeContact {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		MergeContact objMrgContact = new MergeContact();

		// Step 0: Configure the Driver
		ChromeDriver mainDriver = objMrgContact.ConfigureDriver();
		Thread.sleep(2000);
		
		// Step 1: Enter UserName and Password Using Id Locator
		// Step 2: Click on Login Button using Class Locator
		mainDriver.findElement(By.xpath("//input[@id='username']")).sendKeys("demosalesmanager");
		mainDriver.findElement(By.xpath("//input[@id='password']")).sendKeys("crmsfa");
		mainDriver.findElement(By.xpath("//input[@class='decorativeSubmit']")).click();

		// Step 3: Click on CRM/SFA Link
		mainDriver.findElement(By.xpath("//a[contains(text(),'CRM/SFA')]")).click();
		
		// Step 4: Click on contacts Button
		mainDriver.findElement(By.xpath("//a[text()='Contacts']")).click();
		
		// Step 5: Click on Merge Contacts using Xpath Locator
		mainDriver.findElement(By.xpath("//a[text()='Merge Contacts']")).click();
		
		// Step 6: Click on Widget of FROM Contact	-->	This will open another window
		System.out.println("Now in main window : " + mainDriver.getTitle());
		mainDriver.findElement(By.xpath("//table[@class='twoColumnForm']/tbody/tr[1]/td[2]/a/img")).click();
		
		// Step 7: WINDOW HANDLING section
		Set<String> windowHandlesSet1 = mainDriver.getWindowHandles();
		List<String> windowHandlesList1 = new ArrayList<String>(windowHandlesSet1);
		
		//windowHandlesList.get(1); // window handle of the child window
		// Main window - 0  // child windows - 1
		mainDriver.switchTo().window(windowHandlesList1.get(1));
		System.out.println("Now in the first pop-up window : " + mainDriver.getTitle());
		
		// Step 8: Click on First Resulting Contact
		mainDriver.findElement(By.xpath("(//table[@class='x-grid3-row-table'])[1]/tbody/tr/td[1]/div/a")).click();		
		
		// Step 9: Click on Widget of TO Contact	-->	This will open another window
		mainDriver.switchTo().window(windowHandlesList1.get(0));
		System.out.println("Now back in main window : " + mainDriver.getTitle());
		mainDriver.findElement(By.xpath("//table[@class='twoColumnForm']/tbody/tr[2]/td[2]/a/img")).click();
		
		// Step 10: Click on Second Resulting Contact
		Set<String> windowHandlesSet2 = mainDriver.getWindowHandles();
		List<String> windowHandlesList2 = new ArrayList<String>(windowHandlesSet2);
		mainDriver.switchTo().window(windowHandlesList2.get(1));
		System.out.println("Now in the second pop-up window : " + mainDriver.getTitle());
		mainDriver.findElement(By.xpath("(//table[@class='x-grid3-row-table'])[2]/tbody/tr/td[1]/div/a")).click();
		
		// Step 11: Click on Merge button using Xpath Locator
		mainDriver.switchTo().window(windowHandlesList2.get(0));
		System.out.println("Now back in main window : " + mainDriver.getTitle());
		mainDriver.findElement(By.xpath("//a[text()='Merge']")).click();
		Thread.sleep(1000);
		
		// Step 12. Accept the Alert
		Alert alertDialog = mainDriver.switchTo().alert();
		alertDialog.accept();
		Thread.sleep(1000);
		
		//13. Verify the title of the page
		String strPgTitle = mainDriver.getTitle();
		if (strPgTitle.equals("View Contact | opentaps CRM")) {
			System.out.println("Correct page loaded. The page title is : " + strPgTitle);
		}
		else {
			System.out.println("Something's not right; incorrect page loaded. The page title is : " + strPgTitle);
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
		driver.get("http://leaftaps.com/opentaps/control/login");
		// Step 0-04: Maximize the window
		driver.manage().window().maximize();
		// Step 0-05: waits for 10 secs if the element is not in the DOM
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}
	
}
