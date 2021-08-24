package com.revature.services.taskFeatures;

import com.intuit.karate.junit5.Karate;

public class TaskRunner {

	@Karate.Test
	Karate testSetSprintLength() {
		return Karate.run("Task").relativeTo(getClass());
	}
}
