package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class ChangeRoleRunner {
	@Karate.Test
	Karate testChangeRole() {
		return Karate.run("ChangeRole").relativeTo(getClass());
	}
}
