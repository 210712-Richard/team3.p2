package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class ViewUserRunner {
	@Karate.Test
	Karate testViewUser() {
		return Karate.run("ViewUser").relativeTo(getClass());
	}
}
