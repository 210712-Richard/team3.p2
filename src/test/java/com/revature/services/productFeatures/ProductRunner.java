package com.revature.services.ProductFeatures;

import com.intuit.karate.junit5.Karate;

public class ProductRunner {
	
	@Karate.Test
	Karate testAssignFeature() {
		return Karate.run("Product").relativeTo(getClass());
		
	}

}
