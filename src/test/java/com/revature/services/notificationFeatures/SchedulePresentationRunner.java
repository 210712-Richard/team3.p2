package com.revature.services.notificationFeatures;

import com.intuit.karate.junit5.Karate;

public class SchedulePresentationRunner {
	
	@Karate.Test
	Karate testSchedulePresentationFeature() {
		return Karate.run("SchedulePresentation").relativeTo(getClass());
		
	}
	

}
