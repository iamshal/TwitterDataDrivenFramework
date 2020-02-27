package com.pages;

import com.base.Page;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends Page {

	public LoginPage dologin(String username, String password) throws InterruptedException {
		wait("1");
		clearCookies();
		wait("3");
		test.log(LogStatus.INFO, "Invalid username : " + username + " invalid password " + password);
		click("ClickLogin_XPATH");
		type("email_XPATH", username);
		type("password_XPATH", password);
		test.log(LogStatus.INFO, "login successful");
		click("login_XPATH");
		test.log(LogStatus.INFO, "login successful");
		wait("3");

		return new LoginPage();

	}

}
