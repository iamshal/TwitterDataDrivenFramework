package testcases;

import org.testng.annotations.Test;

import com.pages.Homepage;

public class DeleteTweetTest extends BaseTest {
	@Test
	public void deleteTweetTest() throws InterruptedException {

		Homepage ap = new Homepage();
		ap.DeleteTweet();
	}
}
