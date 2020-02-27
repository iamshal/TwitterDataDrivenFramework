package testcases;

import org.testng.annotations.AfterSuite;

import com.base.Page;

public class BaseTest {

	@AfterSuite
	public void teardown() {

		Page.quit();

	}
}
