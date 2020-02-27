package testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.pages.LoginPage;
import com.utilities.TestUtil;

public class InvalidLoginTest extends BaseTest {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void loginTest(Hashtable<String, String> data) throws InterruptedException {

		LoginPage lp = new LoginPage();
		lp.dologin(data.get("username"), data.get("password"));

	}

 

}
