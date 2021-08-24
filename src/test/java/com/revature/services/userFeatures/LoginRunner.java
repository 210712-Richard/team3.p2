package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class LoginRunner {
		@Karate.Test
		Karate testLogin() {
			return Karate.run("Login").relativeTo(getClass());
		}
}
