package testcases;

import org.testng.annotations.Test;

import com.pages.Homepage;

public class ReplyTweetTest extends BaseTest {
	@Test
	public void replyTweetTest() throws InterruptedException {

		Homepage ap = new Homepage();
		ap.ReplyTweet();
	}
}
