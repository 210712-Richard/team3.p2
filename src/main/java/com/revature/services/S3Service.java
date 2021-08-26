package com.revature.services;

import java.util.List;

import com.revature.beans.Sprint;

public interface S3Service {
	void uploadToBucket(String key, Object o);
	List<Sprint> getSprints(String... key);
}
