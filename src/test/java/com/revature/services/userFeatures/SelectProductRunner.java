package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class SelectProductRunner {
		@Karate.Test
		Karate testLogin() {
			return Karate.run("SelectProduct").relativeTo(getClass());
		}
}
