package testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.pages.Homepage;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.TestUtil;

public class CreateTweetTest extends BaseTest {
	@Test
	public void createTweetTest() throws InterruptedException {

		Homepage ap = new Homepage();
		ap.CreateTweet();

	}

}
