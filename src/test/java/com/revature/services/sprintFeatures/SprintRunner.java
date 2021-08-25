package com.revature.services.sprintFeatures;

import com.intuit.karate.junit5.Karate;

public class SprintRunner {
	
	@Karate.Test
	Karate testSprintFeature() {
		return Karate.run("Sprint").relativeTo(getClass());
		
	}

}
