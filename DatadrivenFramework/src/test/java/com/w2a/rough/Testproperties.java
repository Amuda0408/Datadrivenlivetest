package com.w2a.rough;
//This class is used to do test propertiesfiles
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Testproperties {
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		Properties OR = new Properties();
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		System.out.println("config: "+ fis);
		config.load(fis);
		fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		System.out.println("OR: "+ fis);
		OR.load(fis);
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("BkmLBtn"));
				
	}

}
