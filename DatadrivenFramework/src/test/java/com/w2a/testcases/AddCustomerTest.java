package com.w2a.testcases;

import java.util.Hashtable;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.Alert;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
//import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.Utilities.ExcelReader;
import com.w2a.Utilities.TestUtil;
//import com.w2a.Utilities.TestUtil;
import com.w2a.base.TestBase;

public class AddCustomerTest extends TestBase {
	
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException{
		
		if(!data.get("runmode").equals("Y")){
			
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}
		
		//System.out.println("Name: " + data.get("firstName"));
		//System.out.println("lastname: " + data.get("lastName"));
		//System.out.println("postcode: " + data.get("postcode"));
		click("addCustBtn_CSS");
		type("firstName_CSS",data.get("firstName"));
		type("lastName_XPATH",data.get("lastName"));
		type("postcode_CSS",data.get("postcode"));
		Thread.sleep(5000);
		click("AddBtn_CSS");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		
		Thread.sleep(2000);
		
	}
	
	

}
