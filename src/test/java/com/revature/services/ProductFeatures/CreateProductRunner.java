package com.revature.services.ProductFeatures;

import com.intuit.karate.junit5.Karate;

public class CreateProductRunner {
	
	@Karate.Test
	Karate testAssignFeature() {
		return Karate.run("CreateProduct").relativeTo(getClass());
		
	}

}
