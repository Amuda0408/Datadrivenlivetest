package com.w2a.rough;

import java.util.Date;

public class TestTimeStamp {

	public static void main(String[] args) {
		//verify Time stamp on screen shot
		Date d= new Date();
		//convert date to string
		String screenshotName =d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		System.out.println(screenshotName);
		System.out.println(d);

	}

}
