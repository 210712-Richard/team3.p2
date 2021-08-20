package com.revature.services.ScrumFeatures;

import com.intuit.karate.junit5.Karate;

public class AssignRunner {
	
	@Karate.Test
	Karate testAssignFeature() {
		return Karate.run("AssignTask").relativeTo(getClass());
		
	}
	

}
