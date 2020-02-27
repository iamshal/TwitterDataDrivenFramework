package testcases;

import org.testng.annotations.Test;

import com.pages.Homepage;

public class LikeTweetTest extends BaseTest {
	@Test
	public void likeTweetTest() throws InterruptedException {

		Homepage ap = new Homepage();
		ap.likeTweet();
	}
}
