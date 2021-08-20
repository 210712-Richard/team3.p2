package com.revature.services.NotificationFeatures;

import com.intuit.karate.junit5.Karate;

public class CheckNotificationRunner {
	
	@Karate.Test
	Karate testCheckNotificationFeature() {
		return Karate.run("CheckNotification").relativeTo(getClass());
		
	}
	

}
