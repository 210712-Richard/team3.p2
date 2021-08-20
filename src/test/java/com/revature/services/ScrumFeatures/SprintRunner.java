package com.revature.services.ScrumFeatures;

import com.intuit.karate.junit5.Karate;

public class SprintRunner {

	@Karate.Test
	Karate testSetSprintLength() {
		return Karate.run("Sprint").relativeTo(getClass());
	}
}
