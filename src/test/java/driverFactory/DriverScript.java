package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.Functionlibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class DriverScript extends AppUtil {
String inputpath = "./FileInput/LoginData.xlsx";
String outputpath = "./FileOutput/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;
@Test
public void startTest()throws Throwable
{
	//define path of html reports
	report = new ExtentReports("./target/Reports/LoginReports.html");
	//create object Excelfileutil
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in login sheet
	int rc = xl.rowCount("Login");
	Reporter.log("No of row::"+rc, true);
	//iterate all row in login sheet
	for(int i=1;i<=rc;i++)
	{
		logger=report.startTest("Validate Login");
		logger.assignAuthor("Guddu");
		String username = xl.getCellData("Login", i, 0);
		String password = xl.getCellData("Login", i, 1);
		logger.log(LogStatus.INFO, username+"---"+password);
		//call login method from function library class
		boolean res = Functionlibrary.adminLogin(username,password);
		if(res)
		{
			//if res is true then write login success into result cell 
			xl.setCellData("Login", i, 2, "Login Success", outputpath);
			//if res is true then write pass into status cell
			xl.setCellData("Login", i, 3,"Pass" , outputpath);
			logger.log(LogStatus.PASS, "Username and Password are Valid");
			
		}
		else
		{
			File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
			//if res is fail then write login fail into result cell
			xl.setCellData("Login", i, 2, "Login Fail", outputpath);
			//if res is fail then write Fail into status cell
			xl.setCellData("Login", i, 3, "Fail", outputpath);
			logger.log(LogStatus.FAIL, "Username and Password are not Valid");
			
			
			
		}
		 report.endTest(logger);
		 report.flush();
	}
	
}

}
