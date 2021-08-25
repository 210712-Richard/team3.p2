package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class ScrumBoardSelectRunner {
	
	@Karate.Test
	Karate testProductSelect() {
		return Karate.run("ScrumBoardSelect").relativeTo(getClass());
	}
}
