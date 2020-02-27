package listeners;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.base.Page;

import com.relevantcodes.extentreports.LogStatus;
import com.utilities.TestUtil;

public class CustomListeners extends Page implements ITestListener, ISuiteListener {

	public String messageBody;

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " Failed with exception : " + arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
				+ " height=200 width=200></img></a>");
		rep.endTest(test);
		rep.flush();

	}

	public void onTestSkipped(ITestResult arg0) {

		test.log(LogStatus.SKIP, arg0.getName().toUpperCase() + " Skipped the test as the Run mode is NO");
		rep.endTest(test);
		rep.flush();

	}

	public void onTestStart(ITestResult arg0) {

		test = rep.startTest(arg0.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult arg0) {

		test.log(LogStatus.PASS, arg0.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();

	}

	public void onFinish(ISuite arg0) {

	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub

	}

}
