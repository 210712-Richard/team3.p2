package com.revature.services.sprintsFeatures;

import com.intuit.karate.junit5.Karate;

public class SprintRunner {

	@Karate.Test
	Karate testSprintFeature() {
		return Karate.run("sprints").relativeTo(getClass());
	}
}
