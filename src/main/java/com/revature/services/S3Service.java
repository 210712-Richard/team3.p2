package com.revature.services;

import com.revature.beans.Sprint;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface S3Service {
	Mono<Void> uploadToBucket(String key, Object o);
	Flux<Sprint> getSprints(String... key);
}
