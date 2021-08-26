package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class RegisterRunner {
	@Karate.Test
	Karate testViewProducts() {
		return Karate.run("Register").relativeTo(getClass());
	}
}
