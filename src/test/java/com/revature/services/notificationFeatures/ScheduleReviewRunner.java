package com.revature.services.notificationFeatures;

import com.intuit.karate.junit5.Karate;

public class ScheduleReviewRunner {
	
	@Karate.Test
	Karate testScheduleReviewFeature() {
		return Karate.run("ScheduleReview").relativeTo(getClass());
		
	}

}