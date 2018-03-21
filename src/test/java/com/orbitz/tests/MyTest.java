package com.orbitz.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.orbitz.flights.FlightBooking;

public class MyTest {

	public WebDriver driver;
	FlightBooking flightBook;
	@Test
	public void test(){
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("https://www.orbitz.com/");
		flightBook = new FlightBooking(driver);
		flightBook.clickFlightsFromMenu();
		flightBook.selectTripType();
		flightBook.enterOriginAs("Pune");
		flightBook.EnterDestinationAs("Tirupati");
		flightBook.clickDepart();
		String SelectDate = "04/24/2018";
		Date d = new Date(SelectDate);
		SimpleDateFormat dt = new SimpleDateFormat("MMM/dd/yyyy");
		String date = dt.format(d);
		String[] split = date.split("/");
		String txtDay =  split[1];
		String txtMont_year = split[0]+" "+split[2];
		flightBook.selectDepartDate(txtMont_year, txtDay);
		flightBook.clickSearchButton();
		List<WebElement> availableFlights = flightBook.listOfFlights();
		Assert.assertTrue(availableFlights.size()>=2);
		System.out.println(availableFlights.size());

	}
}
