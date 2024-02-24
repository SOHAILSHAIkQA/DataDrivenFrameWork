package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class Functionlibrary extends AppUtil{
public static boolean adminLogin(String user,String pass)throws Throwable
{
	driver.get(conpro.getProperty("Url"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.xpath(conpro.getProperty("Objresetbutton"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Objuser"))).sendKeys(user);
	driver.findElement(By.xpath(conpro.getProperty("Objpass"))).sendKeys(pass);
	driver.findElement(By.xpath(conpro.getProperty("Objlogin"))).click();
	Thread.sleep(3000);
	String Expected ="dashboard";
	String Actual = driver.getCurrentUrl();
	if(Actual.contains(Expected))
	{
		Reporter.log("Valid Username and Valid Password::"+Expected+"---"+Actual, true);
		//click logout
		driver.findElement(By.xpath(conpro.getProperty("Objlogout"))).click();
		return true;
	}
	else
	{
		//capture message
		String message = driver.findElement(By.xpath(conpro.getProperty("Objmessage"))).getText();
		Thread.sleep(3000);
		//click ok button
		driver.findElement(By.xpath(conpro.getProperty("ObjOkbutton"))).click();
		Reporter.log(message+"---"+Expected+"---"+Actual, true);
		return false;
		
	}
	
}


}
