package com.w2a.testcases;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class LoginTest extends TestBase {
	
@Test
public void loginTest() throws InterruptedException, IOException {
	//verifyEquals("abc", "xyz");
	//Thread.sleep(3000);
	
	log.debug("Inside Login Test");
	//instead typing this entire line just call the method
	//	driver.findElement(By.cssSelector(OR.getProperty("BkmLBtn"))).click();
	click("BkmLBtn_CSS");
	
	Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");
	//this is not necessary but for time being,t make sure it click the button
	Thread.sleep(3000);
	log.debug("element present");
	log.debug("Login successfully executed");
	//Assert.fail("Login is not successful");
}

	
}	





	

