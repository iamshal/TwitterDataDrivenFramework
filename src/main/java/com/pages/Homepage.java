package com.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.base.Page;
import com.relevantcodes.extentreports.LogStatus;

public class Homepage extends Page {

	public void CreateTweet() {
		wait("1");
		click("CreateInputTweet_XPATH");
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);

		type("CreateInputTweet_XPATH", strDate);
		click("Tweet_XPATH");

	}

	public void likeTweet() {
		wait("4");
		click("LikeTweet_XPATH");

	}

	public void ReplyTweet() {
		wait("4");
		click("ReplyTweet_XPATH");
//	click("Reply_XPATH");
		type("AddAnotherTweet_XPATH", "Reply Tweet Message");
		click("ReplyTweetMessage_XPATH");

	}

	public void DeleteTweet() {
		wait("2");
		click("SVGDropdown_XPATH");
		wait("3");
		click("DeleteTweet_XPATH");
		wait("10");
	}

}