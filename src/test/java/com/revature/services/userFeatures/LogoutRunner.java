package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class LogoutRunner {
	@Karate.Test
	Karate testLogout() {
		return Karate.run("Logout").relativeTo(getClass());
	}
}
