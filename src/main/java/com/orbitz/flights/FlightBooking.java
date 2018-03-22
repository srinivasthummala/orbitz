package com.orbitz.flights;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightBooking {
	
	public WebDriverWait wait;
	WebDriver driver;

	public FlightBooking(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 30);

	}

	// click Flights from menu items
	public void clickFlightsFromMenu() {
		driver.findElement(By.xpath("//*[contains(@id, 'tab-flight-tab')]")).click();
	}

	// select one-way trip type from radio buttons
	public void selectTripType() {
		driver.findElement(By.xpath("//input[contains(@id, 'flight-type-one-way')][@type='radio']/..")).click();
	}

	// Enter text into Flying from
	public void enterOriginAs(String name) {
		String originXpath = "//span[contains(text(), 'Flying from')]/following-sibling::*[starts-with(@id, 'flight-origin')]";
		WebElement origin = driver.findElement(By.xpath(originXpath));
		origin.clear();
		origin.sendKeys(name);
		String originValueXpath = "//a[contains(@data-value, '" + name + "')]";
		WebElement originValue = driver.findElement(By.xpath(originValueXpath));
		wait.until(ExpectedConditions.elementToBeClickable(originValue)).click();
	}

	// Enter text into Flying to
	public void EnterDestinationAs(String name) {
		String destXpath = "//span[contains(text(), 'Flying to')]/following-sibling::*[starts-with(@id, 'flight-destination')]";
		WebElement destination = driver.findElement(By.xpath(destXpath));
		destination.clear();
		destination.sendKeys(name);
		WebElement destinationValue = driver.findElement(By.xpath("//a[contains(@data-value, '" + name + "')]"));
		wait.until(ExpectedConditions.elementToBeClickable(destinationValue)).click();
	}

	//Click  departing date for select date from calendar
	public void clickDepart(){
		String departXpath = "//span[contains(text(), 'Departing')]"
							 + "/following-sibling::input[starts-with(@id, 'flight-departing')]";
		WebElement txtDate = driver.findElement(By.xpath(departXpath));
		txtDate.clear();
		txtDate.click();
	}
	
	// Select date from calendar
	public void selectDepartDate(String monthValue, String dayValue) {
		String monthsTitleXpath = "//div[@class='datepicker-cal-month']/table[@class='datepicker-cal-weeks']/caption";
		List<WebElement> lstMonthsTable = driver.findElements(By.xpath(monthsTitleXpath));
		
		for (int i=1; i<=lstMonthsTable.size(); i++) {
			String monthText = lstMonthsTable.get(i-1).getText();
			if (monthText.equalsIgnoreCase(monthValue))	{
				String daysXpath = "//div[@class='datepicker-cal-month']["+i+"]/table[@class='datepicker-cal-weeks']"
								+ "//td/button[@class='datepicker-cal-date']";
				List<WebElement> lstDates = driver.findElements(By.xpath(daysXpath));
				for (WebElement date : lstDates) {
					
					if (date.getText().contains(dayValue)) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);

						return;
					}
				}
			}
		
		}
		Actions builder = new Actions(driver);
		String btnNextXpath = "//div[@class='datepicker-dropdown']/div[@class='datepicker-cal']"
							+ "/button[@class='datepicker-paging datepicker-next btn-paging btn-secondary next']";
		WebElement btnNext = driver.findElement(By.xpath(btnNextXpath));
		builder.moveToElement(btnNext).click().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		selectDepartDate(monthValue, dayValue);
		
	}
	
	//Click search button
	public void clickSearchButton(){
		WebElement btnSearch = driver.findElement(By.xpath("//button[contains(@class, 'btn-action')]/span[text()='Search']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSearch);	

	}
	
	//find and return list of flights available
	public List<WebElement> listOfFlights(){
		List<WebElement> flightsList = driver.findElements(By.xpath("//div[@id='flight-listing-container']/ul/li/h3/.."));
		return flightsList;
	}
	
	//Quit driver 
	public void quitBrowser(){
		driver.quit();
	}
	
	//Take screenshot 
	public void takeScreenShot(String screenShotName) {
		String timeStamp;
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); 
		try {
			FileUtils.copyFile(sourceFile,
					new File(System.getProperty("user.dir") + "/screenshot/" + screenShotName + "_" +timeStamp+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
