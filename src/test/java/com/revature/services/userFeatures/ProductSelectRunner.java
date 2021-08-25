package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class ProductSelectRunner {
	@Karate.Test
	Karate testProductSelect() {
		return Karate.run("ProductSelect").relativeTo(getClass());
	}
}
